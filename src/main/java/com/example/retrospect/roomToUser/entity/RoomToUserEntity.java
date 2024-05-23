package com.example.retrospect.roomToUser.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "room-to-user")
public class RoomToUserEntity {

    @EmbeddedId
    private RoomToUserId id;
}
