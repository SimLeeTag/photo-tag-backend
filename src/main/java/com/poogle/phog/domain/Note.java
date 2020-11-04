package com.poogle.phog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTE_ID")
    private Long id;

    private Boolean isDeleted;

    private String rawMemo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Photo> photos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NoteTag> noteTags = new ArrayList<>();

    @Builder
    public Note(User user, Boolean isDeleted, String rawMemo, LocalDateTime created, List<Photo> photos) {
        this.user = user;
        this.isDeleted = isDeleted;
        this.rawMemo = rawMemo;
        this.created = created;
        this.photos = photos;
    }

    public void addPhotos(List<Photo> photos) {
        this.photos = photos;
        if (this.photos != null && this.photos.size() > 0) {
            for (Photo photo : photos) {
                photo.setNote(this);
            }
        }
    }

    public List<String> captureTags(Note note) {
        String memo = note.getRawMemo();
        Pattern pattern = Pattern.compile("#[A-z가-힣]*");
        Matcher matcher = pattern.matcher(memo);
        List<String> tags = new ArrayList<>();
        while (matcher.find()) {
            tags.add(matcher.group().trim().replace("#", ""));
        }
        return tags;
    }
}
