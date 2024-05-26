package com.example.retrospect.roomToUser.repository;

import com.example.retrospect.createchatroom.entity.CreateRoomEntity;
import com.example.retrospect.roomToUser.entity.RoomToUserEntity;
import com.example.retrospect.roomToUser.entity.RoomToUserId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IRoomToUserRepository extends JpaRepository<RoomToUserEntity, RoomToUserId> {
    List<RoomToUserEntity> findAllByIdCreateRoomEntity_CreateRoomEntity_RoomId(Long roomId);

    @Modifying
    @Transactional
    @Query("DELETE FROM RoomToUserEntity r WHERE r.id.createRoomEntity.roomId = :roomId")
    void deleteByRoomId(Long roomId);

    @Modifying
    @Transactional
    @Query("DELETE FROM AccessControl ac WHERE ac.room.id = :roomId")
    void deleteAccessControlByRoomId(Long roomId);
}
