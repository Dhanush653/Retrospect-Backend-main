package com.example.retrospect.roomToUser.repository;

import com.example.retrospect.roomToUser.entity.RoomToUserEntity;
import com.example.retrospect.roomToUser.entity.RoomToUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IRoomToUserRepository extends JpaRepository<RoomToUserEntity, RoomToUserId> {
    List<RoomToUserEntity> findAllByIdCreateRoomEntity_CreateRoomEntity_RoomId(Long roomId);
}
