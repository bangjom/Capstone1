package com.capstone.studywithme.service;

import com.capstone.studywithme.Exception.PasswordWrongException;
import com.capstone.studywithme.domain.Member;
import com.capstone.studywithme.domain.Room;
import com.capstone.studywithme.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Room authenticate(String email, String name, String password){
        System.out.println(name+" "+password);
        List<Room> findRooms = roomRepository.findByName(name);
        System.out.println(findRooms.get(0).getName());
        if(findRooms.isEmpty()){
            throw new IllegalStateException("존재하지 않는 방 입니다.");
        }
        if(!findRooms.isEmpty() && !findRooms.get(0).getIs_private()){
            return findRooms.get(0);
        }
        if(!passwordEncoder.matches(password,findRooms.get(0).getPasscode())){
            throw new PasswordWrongException();
        }
        memberService.updateCoin(memberService.findOneByEmail(email), Long.valueOf(-100));
        return findRooms.get(0);
    }

    @Transactional
    public Long makeRoom(String email, Room room){
        validateDuplicateRoom(room);

        if(room.getIs_private() == true) {
            if (room.getPasscode() != null)
                room.setPasscode(passwordEncoder.encode(room.getPasscode()));
            else
                room.setPasscode("");
            memberService.updateCoin(memberService.findOneByEmail(email), Long.valueOf(-200));
        }
        else
            room.setPasscode(null);

        roomRepository.save(room);
        return room.getId();
    }

    private void validateDuplicateRoom(Room room){
        List<Room> findRooms = roomRepository.findByName(room.getName());
        if(!findRooms.isEmpty()){
            throw new IllegalStateException("이미 존재하는 방이름입니다.");
        }
    }

    public List<Room> findRooms(){return roomRepository.findAll();}

    public Room findOneByName(String name){return roomRepository.findByName(name).get(0);}

    public Room findOne(Long roomId){return roomRepository.findOne(roomId);}

    @Transactional
    public void update(Long id, String name, String passcode, Boolean is_private){
        Room room = roomRepository.findOne(id);

        if(is_private == true) {
            if(passcode == null)
                room.setPasscode("");
            else
                room.setPasscode(passcode);
            room.setPasscode(passwordEncoder.encode(room.getPasscode()));
            room.setIs_private(true);
        }
        else{
            room.setPasscode(null);
            room.setIs_private(false);
        }

        room.setName(name);
        room.setUpdated_at(LocalDateTime.now());
    }

}
