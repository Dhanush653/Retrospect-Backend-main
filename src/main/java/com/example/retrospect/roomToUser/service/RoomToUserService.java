package com.example.retrospect.roomToUser.service;


import com.example.retrospect.createchatroom.entity.CreateRoomEntity;
import com.example.retrospect.createchatroom.repository.IRoomRepository;
import com.example.retrospect.roomToUser.dto.UserRoomJoinDTO;
import com.example.retrospect.roomToUser.entity.RoomToUserEntity;
import com.example.retrospect.roomToUser.entity.RoomToUserId;
import com.example.retrospect.roomToUser.repository.IRoomToUserRepository;
import com.example.retrospect.user.entity.UserEntity;
import com.example.retrospect.user.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class RoomToUserService implements IRoomToUserService {
    @Autowired
    IRoomToUserRepository iRoomToUserRepository;

    @Autowired
    IRoomRepository roomRepository;

    @Autowired
    IUserRepository userRepository;

    @Override
    public HashMap<String, String> UserJoinedRoom(UserRoomJoinDTO userRoomJoinDTO) {
        Optional<CreateRoomEntity> roomOptional = roomRepository.findById(Long.valueOf(userRoomJoinDTO.getRoomId()));
        Optional<UserEntity> userOptional = userRepository.findById(Integer.valueOf(userRoomJoinDTO.getUserId()));
        HashMap<String, String> map = new HashMap<>();

        if (roomOptional.isPresent() && userOptional.isPresent()) {
            CreateRoomEntity roomEntity = roomOptional.get();
            UserEntity userEntity = userOptional.get();

            RoomToUserId roomToUserId = new RoomToUserId();
            roomToUserId.setCreateRoomEntity(roomEntity);
            roomToUserId.setUserEntity(userEntity);

            LocalDateTime timeStamp = LocalDateTime.now();
            roomToUserId.setTimeStamp(timeStamp.toString());

            try {
                RoomToUserEntity roomToUserEntity = new RoomToUserEntity();
                roomToUserEntity.setId(roomToUserId);
                iRoomToUserRepository.save(roomToUserEntity);
                map.put("status", "success");
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", "error :" + e.getMessage());
            }
        } else {
            map.put("status", "error: Room or User not found");
        }
        return map;
    }



    @Override
    public List<RoomToUserEntity> usersInRoom(String roomId) {
        return iRoomToUserRepository.findAllByIdCreateRoomEntity_CreateRoomEntity_RoomId(Long.valueOf(roomId));
    }

}

