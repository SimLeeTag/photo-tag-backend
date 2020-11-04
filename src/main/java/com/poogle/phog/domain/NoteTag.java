package com.poogle.phog.domain;

import lombok.Builder;
import lombok.ToString;

import javax.persistence.*;

@ToString(exclude = {"note", "tag"})
@Entity
public class NoteTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTE_TAG_ID")
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "NOTE_ID")
    private Note note;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
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
