package com.capstone.studywithme.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "rooms")
public class Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private long id;

    @Column(name = "passcode", nullable = false)
    private String passcode;

    @Column(name= "invite_link", nullable = false)
    private String invite_link;

    @Column(name = "is_pivate", nullable = false)
    private boolean is_private;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updated_at;


}
