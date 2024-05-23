package com.example.retrospect.topic.entity;

import com.example.retrospect.createchatroom.entity.CreateRoomEntity;
import com.example.retrospect.websockets.entity.Message;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class TopicEntity {
    @Id

    private long topicId;
    private String topicName;




}
