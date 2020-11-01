package com.poogle.phog.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "TAG")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAG_ID")
    private Long tagId;

    @Column(name = "TAG_NAME")
    private String tagName;

    private Boolean activated;

    private Long userId;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NoteTag> noteTags = new ArrayList<>();

    @Builder
    public Tag(String tagName, Boolean activated, Long userId) {
        this.tagName = tagName;
        this.activated = activated;
        this.userId = userId;
    }
}
