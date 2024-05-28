package com.example.retrospect.topic.controller;


import  com.example.retrospect.topic.dto.TopicDTO;
import com.example.retrospect.topic.entity.TopicEntity;
import com.example.retrospect.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topic")
@CrossOrigin(allowedHeaders = "*")
public class TopicController {

    @Autowired
    private TopicService topicService;
    @GetMapping("/getAllTopic")
    public List<TopicEntity> getAllTopic() {
        return topicService.getAllTopics();
    }
 
    @PostMapping("/addTopic")
    public ResponseEntity<?> addTopic(@RequestBody TopicDTO topicDto) {
        try {
            topicService.addTopic(topicDto);
            return ResponseEntity.ok().body("Topic added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add topic");
        }
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<TopicEntity>> getTopicsByRoomId(@PathVariable String roomId) {
        try {
            List<TopicEntity> topics = topicService.getTopicsByRoomId(roomId);
            return ResponseEntity.ok(topics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
