package com.capstone.studywithme.repository;

import com.capstone.studywithme.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomRepository {
    private final EntityManager em;

    public void save(Room room){em.persist(room);}

    public Room findOne(Long id){return em.find(Room.class,id);}

    public List<Room> findAll(){
        return em.createQuery("select m from Room m", Room.class)
                .getResultList();
    }

    public List<Room> findByName(String name){
        return em.createQuery("select m from Room m where m.name = :name", Room.class)
                .setParameter("name",name)
                .getResultList();
    }
}
