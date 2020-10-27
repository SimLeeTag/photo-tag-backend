package com.poogle.phog.domain;

import javax.persistence.*;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAG_ID")
    private Long id;

    private String tagName;

    private Boolean activated;

    public Tag() {
    }
}
