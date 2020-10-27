package com.poogle.phog.domain;

import javax.persistence.*;

@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PHOTO_ID")
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "NOTE_ID")
    private Note note;

    //연관관계 편의 메소드
    public void addPhoto(Note note) {
        this.note = note;
        note.getPhotos().add(this);
    }

    public Photo() {
    }
}
