package com.poogle.phog.domain;

import lombok.Builder;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Setter
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

    @Builder
    public Photo(String url) {
        this.url = url;
    }

    public Photo() {
    }
}
