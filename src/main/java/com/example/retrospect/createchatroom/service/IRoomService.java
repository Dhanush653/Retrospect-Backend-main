package com.example.retrospect.createchatroom.service;

import com.example.retrospect.createchatroom.entity.CreateRoomEntity;

import java.util.List;

import com.example.retrospect.createchatroom.dto.RoomDTO;
import com.example.retrospect.createchatroom.entity.CreateRoomEntity;

import java.util.List;
import java.util.Set;

public interface IRoomService {
    List<CreateRoomEntity> getAllRooms();

    CreateRoomEntity createRoom(RoomDTO roomDTO);

    CreateRoomEntity updateRoom(long  roomId, CreateRoomEntity updatedRoomEntity);

    CreateRoomEntity convertDtoToEntity(RoomDTO roomDTO);

    boolean checkRoomAccess(String email, long roomId);
    CreateRoomEntity getRoomById(long  roomId);
}