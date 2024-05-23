package com.example.retrospect.roomToUser.controller;


import com.example.retrospect.roomToUser.dto.UserInRoom;
import com.example.retrospect.roomToUser.dto.UserRoomJoinDTO;
import com.example.retrospect.roomToUser.entity.RoomToUserEntity;
import com.example.retrospect.roomToUser.service.RoomToUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(allowedHeaders = "*")
public class RoomToUserController {
    @Autowired
    private RoomToUserService service;

    @PostMapping("/userJoinRoom")
    public HashMap<String, String> userJoinRoom(@RequestBody UserRoomJoinDTO userRoomJoinDTO) {
        return service.UserJoinedRoom(userRoomJoinDTO);
    }

    //    @GetMapping("/usersInRoom/{roomId}")
//    public List<UserInRoom> getUsersInRoom(@PathVariable String roomId) {
//        List<RoomToUserEntity> roomUsers = service.usersInRoom(roomId);
//        List<UserInRoom> usersDTO = new ArrayList<>();
//
//        for (RoomToUserEntity roomUser : roomUsers) {
//            UserInRoom userDTO = new UserInRoom();
//            userDTO.setUserId((long) roomUser.getId().getUserEntity().getUserId());
//            userDTO.setUserName(roomUser.getId().getUserEntity().getUserName());
//            userDTO.setUserEmail(roomUser.getId().getUserEntity().getUserEmail());
//            usersDTO.add(userDTO);
//        }
//
//        return usersDTO;
//    }
    @CrossOrigin(origins = {"http://localhost:3000"})
    @GetMapping("/usersInRoom/{roomId}")
    public List<UserInRoom> getUsersInRoom(@PathVariable String roomId) {
        List<RoomToUserEntity> roomUsers = service.usersInRoom(roomId);
        List<UserInRoom> usersDTO = new ArrayList<>();
        Set<Long> uniqueUserIds = new HashSet<>();

        for (RoomToUserEntity roomUser : roomUsers) {
            long userId = roomUser.getId().getUserEntity().getUserId();
            if (!uniqueUserIds.contains(userId)) {
                uniqueUserIds.add(userId);
                UserInRoom userDTO = new UserInRoom();
                userDTO.setUserId(userId);
                userDTO.setUserName(roomUser.getId().getUserEntity().getUserName());
                userDTO.setUserEmail(roomUser.getId().getUserEntity().getUserEmail());
                usersDTO.add(userDTO);
            }
        }
        return usersDTO;
    }
}