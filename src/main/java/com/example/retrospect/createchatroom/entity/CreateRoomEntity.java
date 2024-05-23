package com.example.retrospect.createchatroom.entity;


import com.example.retrospect.topic.entity.TopicEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name="RoomDetails")
public class CreateRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomId;
    private String roomName;
    private String roomDescription;
    private String roomStatus = "active";
    private LocalDate room_startdate = LocalDate.now();
    private LocalDate room_enddate = LocalDate.now();
    private String access;
    private String roomCreatedBy;

    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference

    private Set<AccessControl> allowedEmails;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateRoomEntity that = (CreateRoomEntity) o;
        return roomId == that.roomId &&
                Objects.equals(roomName, that.roomName);
    }

}