package com.example.retrospect.websockets.entity;


import com.example.retrospect.roomToUser.entity.RoomToUserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message extends BaseModel {
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
    private String content;
    private String contentType;
    private  String room;
    private String username;
    @ManyToOne
    private RoomToUserEntity roomToUser;
}
