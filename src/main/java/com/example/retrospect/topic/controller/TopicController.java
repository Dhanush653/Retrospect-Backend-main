package com.example.retrospect.topic.controller;


import com.example.retrospect.topic.entity.TopicEntity;
import com.example.retrospect.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;



    @GetMapping("/getAllTopic")
    public List<TopicEntity> getAllTopic() {
        return topicService.getAllTopics();
    }

}
