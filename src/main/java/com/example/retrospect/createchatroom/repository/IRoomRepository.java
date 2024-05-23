package com.example.retrospect.createchatroom.repository;

import com.example.retrospect.createchatroom.entity.CreateRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoomRepository extends JpaRepository< CreateRoomEntity, Long> {


}
