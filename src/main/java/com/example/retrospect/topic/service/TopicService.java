package com.example.retrospect.topic.service;

import com.example.retrospect.topic.entity.TopicEntity;
import com.example.retrospect.topic.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TopicService implements ITopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public List<TopicEntity> getAllTopics( ) {

        return topicRepository.findAll();
    }
}
