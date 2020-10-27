package com.poogle.phog.domain;

import javax.persistence.*;

@Entity
public class NoteHasTag {

    @Id
    @GeneratedValue
    @Column(name = "NOTE_TAG_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "NOTE_ID")
    private Note note;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    public NoteHasTag() {
    }
}
