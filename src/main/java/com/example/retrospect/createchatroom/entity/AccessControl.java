package com.example.retrospect.createchatroom.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
@Table(name="AccessControl")
public class AccessControl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accessControlId;

    @Column(name="email")
    private String email;

    @ManyToOne
    @JoinColumn(name="room_id" )
    @JsonBackReference
    private CreateRoomEntity room;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessControl that = (AccessControl) o;
        return accessControlId == that.accessControlId &&
                Objects.equals(email, that.email);
    }

}