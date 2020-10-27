package com.poogle.phog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTE_ID")
    private Long id;

    private Boolean isDeleted;

    @Lob
    private String rawMemoTag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @OneToMany(mappedBy = "note")
    private List<Photo> photos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "note")
    private List<NoteHasTag> noteHasTags = new ArrayList<>();

    public Note() {
    }

}
