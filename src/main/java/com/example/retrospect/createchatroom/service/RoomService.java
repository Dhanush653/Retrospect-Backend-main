package com.example.retrospect.createchatroom.service;

import com.example.retrospect.createchatroom.dto.RoomDTO;
import com.example.retrospect.createchatroom.entity.AccessControl;
import com.example.retrospect.createchatroom.entity.CreateRoomEntity;
import com.example.retrospect.createchatroom.repository.IRoomRepository;
import com.example.retrospect.roomToUser.repository.IRoomToUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class RoomService implements IRoomService{

    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private IRoomToUserRepository roomToUserRepository;


    @Override
    public List<CreateRoomEntity> getAllRooms() {

        return roomRepository.findAll();
    }

    @Override
    @Transactional
    public CreateRoomEntity createRoom(RoomDTO roomDTO) {
        CreateRoomEntity createRoomEntity = convertDtoToEntity(roomDTO);
        // Set roomCreatedBy
        createRoomEntity.setRoomCreatedBy(roomDTO.getRoomCreatedBy()); // assuming roomCreatedBy field exists in RoomDTO
        if ("restricted".equals(roomDTO.getAccess()) && roomDTO.getAllowedEmails() != null) {
            Set<AccessControl> accessControls = roomDTO.getAllowedEmails().stream()
                    .map(email -> {
                        AccessControl ac = new AccessControl();
                        ac.setEmail(email);
                        ac.setRoom(createRoomEntity);
                        return ac;
                    })
                    .collect(Collectors.toSet());
            createRoomEntity.setAllowedEmails(accessControls);
        }
        return roomRepository.save(createRoomEntity);
    }

    @Override

    public CreateRoomEntity updateRoom(long roomId, CreateRoomEntity updatedRoomEntity) {

        Optional<CreateRoomEntity> optionalRoomEntity = roomRepository.findById(roomId);
        if (optionalRoomEntity.isPresent()) {
            CreateRoomEntity roomEntity = optionalRoomEntity.get();
            roomEntity.setRoomName(updatedRoomEntity.getRoomName());
            roomEntity.setRoomDescription(updatedRoomEntity.getRoomDescription());
            roomEntity.setRoom_startdate(updatedRoomEntity.getRoom_startdate());
            roomEntity.setRoomStatus(updatedRoomEntity.getRoomStatus());
            roomEntity.setRoom_enddate(updatedRoomEntity.getRoom_enddate());

            return roomRepository.save(roomEntity);
        } else {
            throw new NoSuchElementException("Room with id " + roomId + " not found");
        }
    }

    @Override
    public CreateRoomEntity convertDtoToEntity(RoomDTO roomDTO) {
        CreateRoomEntity entity = new CreateRoomEntity();
        entity.setRoomName(roomDTO.getRoomName());
        entity.setRoomDescription(roomDTO.getRoomDescription());
        entity.setRoomStatus(roomDTO.getRoomStatus());

        entity.setAccess(roomDTO.getAccess());
        return entity;
    }

    @Override
    public boolean checkRoomAccess(String email, long roomId) {
        CreateRoomEntity room = roomRepository.findById(roomId).orElse(null);
        return room != null && room.getAllowedEmails().stream().anyMatch(e -> e.getEmail().equals(email));
    }
    @Override
    public CreateRoomEntity getRoomById(long roomId) {
        Optional<CreateRoomEntity> optionalRoomEntity = roomRepository.findById(roomId);
        if (optionalRoomEntity.isPresent()) {
            return optionalRoomEntity.get();
        } else {
            throw new NoSuchElementException("Room with id " + roomId + " not found");
        }
    }

    @Transactional
    @Override
    public void deleteRoom(long roomId) {
        Optional<CreateRoomEntity> room = roomRepository.findById(roomId);
        room.ifPresent(createRoomEntity -> {
            roomToUserRepository.deleteByRoomId(roomId);
            roomToUserRepository.deleteAccessControlByRoomId(roomId);
            roomRepository.delete(createRoomEntity);

        });
    }
}