package com.example.retrospect.createchatroom.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "RoomDetails")
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

    @OneToOne(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private CredentialsEntity credentials;

    public void setCredentials(CredentialsEntity credentials) {
        this.credentials = credentials;
        if (credentials != null) {
            credentials.setRoom(this);
        }
    }
}
