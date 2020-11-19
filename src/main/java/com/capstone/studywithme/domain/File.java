package com.capstone.studywithme.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name="files")
public class File {

    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="url")
    private String url;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updated_at;


}
