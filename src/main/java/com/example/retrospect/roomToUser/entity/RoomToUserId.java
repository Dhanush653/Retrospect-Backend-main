package com.example.retrospect.roomToUser.entity;

import com.example.retrospect.createchatroom.entity.CreateRoomEntity;
import com.example.retrospect.user.entity.UserEntity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RoomToUserId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "roomid")
    private CreateRoomEntity createRoomEntity;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity userEntity;

    private String timeStamp;
}
