package com.poogle.phog.domain;

import lombok.Builder;

import javax.persistence.*;

@Entity
public class NoteTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTE_TAG_ID")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "NOTE_ID")
    private Note note;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    @Builder
    public NoteTag(Note note, Tag tag) {
        this.note = note;
        this.tag = tag;
    }

    public NoteTag() {
    }
}
