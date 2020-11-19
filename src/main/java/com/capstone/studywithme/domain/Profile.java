package com.capstone.studywithme.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "profiles")
public class Profile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @Column(name = "phone", nullable = true)
    private String phone;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "members_id")
    private Member member;

}
