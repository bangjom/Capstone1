package com.capstone.studywithme.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name="posts_contents")
public class PostContent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_content_id")
    private long id;

    @OneToOne
    @JoinColumn(name="post_id")
    private Post post;

    @Lob
    @Column(name="content")
    private String content;
}
