package com.poogle.phog.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;
    private String name;
    private String socialId;
    private String email;

    @Builder
    public User(String name, String socialId, String email) {
        this.name = name;
        this.socialId = socialId;
        this.email = email;
    }

}
