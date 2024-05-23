package com.example.retrospect.roomToUser.service;

import com.example.retrospect.roomToUser.dto.UserRoomJoinDTO;
import com.example.retrospect.roomToUser.entity.RoomToUserEntity;

import java.util.HashMap;
import java.util.List;

public interface IRoomToUserService {
    HashMap<String, String> UserJoinedRoom(UserRoomJoinDTO userRoomJoinDTO);

    List<RoomToUserEntity> usersInRoom(String roomId);
}
