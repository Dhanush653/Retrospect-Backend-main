package com.example.retrospect.topic.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class TopicEntity {
    @Id
    @GeneratedValue
    private Long topicId;
    private String roomId;
    private String topicName;
}
