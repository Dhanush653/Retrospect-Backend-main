package com.example.retrospect.createchatroom.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data
@Table(name = "Credentials")
public class CredentialsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "roomId", referencedColumnName = "roomId")
    @JsonBackReference
    private CreateRoomEntity room;

    private String password;
}
