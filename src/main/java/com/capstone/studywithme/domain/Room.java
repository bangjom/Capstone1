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

    @Column(name="name")
    private String name;

    @Column(name = "passcode")
    private String passcode;

    @Column(name= "invite_link")
    private String invite_link;

    @Column(name = "is_private")
    private Boolean is_private;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;


}
