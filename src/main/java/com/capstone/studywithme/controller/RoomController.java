package com.capstone.studywithme.controller;

import com.capstone.studywithme.domain.Member;
import com.capstone.studywithme.domain.Room;
import com.capstone.studywithme.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/make/room")
    public CreateRoomResponse makeRoom(@RequestBody CreateRoomRequest request){
        Room room = new Room();
        room.setName(request.getName());

        if(request.getIs_private() == true) {
            if (request.getPasscode() != null)
                room.setPasscode(request.getPasscode());
            else{
                room.setPasscode("");
            }
        }
        else
            room.setPasscode(null);

        room.setInvite_link("for test");
        room.setIs_private(request.getIs_private());
        room.setCreated_at(LocalDateTime.now());
        room.setUpdated_at(LocalDateTime.now());
        Long id = roomService.makeRoom(room);
        return new CreateRoomResponse(id,room.getName());
    }

    @GetMapping("/rooms")
    public Result checkRoom(){
        List<Room> findRooms= roomService.findRooms();
        List<RoomController.RoomDto> collect = findRooms.stream()
                .map(m -> new RoomController.RoomDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(collect.size(),collect);
    }

    @PutMapping("/rooms/{id}")
    public UpdateRoomResponse updateRoom(
            @PathVariable("id") Long id,
            @RequestBody UpdateRoomRequest request){
        roomService.update(id, request.getName(), request.getPasscode(), request.getIs_private());
        Room findRoom = roomService.findOne(id);
        return new UpdateRoomResponse(findRoom.getId(), findRoom.getName());
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class RoomDto{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class CreateRoomResponse{
        private Long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class CreateRoomRequest{
        @NotEmpty
        private String name;

        private String passcode;

        @NotEmpty
        private Boolean is_private;
    }

    @Data
    @AllArgsConstructor
    static class UpdateRoomResponse{
        private Long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateRoomRequest{
        private String name;
        private String passcode;
        private Boolean is_private;
    }

}
