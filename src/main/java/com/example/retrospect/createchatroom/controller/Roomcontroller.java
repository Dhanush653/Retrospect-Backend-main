package com.example.retrospect.createchatroom.controller;

import com.example.retrospect.createchatroom.dto.RoomAccessRequestDTO;
import com.example.retrospect.createchatroom.dto.RoomDTO;
import com.example.retrospect.createchatroom.entity.CreateRoomEntity;
import com.example.retrospect.createchatroom.repository.IRoomRepository;
import com.example.retrospect.createchatroom.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(allowedHeaders = "*")

public class Roomcontroller {
    @Autowired
    IRoomService iRoomService;

    public Roomcontroller (IRoomService roomService) {
        this.iRoomService = roomService;
    }

    @Autowired
    IRoomRepository iRoomRepository;

    @GetMapping("/rooms")
    List<CreateRoomEntity> getAllRooms(){
        return iRoomService.getAllRooms();
    }


    @PostMapping("/addrooms")

    CreateRoomEntity createRoom(@RequestBody RoomDTO roomDTO){
        return iRoomService.createRoom(roomDTO);
    }

    @PutMapping("/updateRoom/{roomId}")
    public CreateRoomEntity updateRoom(@PathVariable long roomId, @RequestBody CreateRoomEntity updatedRoomEntity){
        return iRoomService.updateRoom(roomId, updatedRoomEntity);
    }

    @PostMapping("/rooms/check-access")
    public String checkRoomAccess(@RequestBody RoomAccessRequestDTO requestDTO) {
        if (iRoomService.checkRoomAccess(requestDTO.getEmail(), requestDTO.getRoomId())) {
            return "access approved";
        } else {
            return "access denied";
        }
    }

    @PutMapping("/getRoomById/{roomId}")
    public CreateRoomEntity getRoomById(@PathVariable long roomId){
        return iRoomService.getRoomById(roomId);
    }

}