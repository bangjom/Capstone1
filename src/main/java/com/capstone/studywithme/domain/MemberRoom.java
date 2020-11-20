package com.capstone.studywithme.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "members_rooms")
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class MemberRoom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_room_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private AuthorityStatus status;
}
