package com.example.retrospect.topic.service;

import com.example.retrospect.topic.dto.TopicDTO;
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

    @Override
    public void addTopic(TopicDTO topicDto) {
        TopicEntity topic = new TopicEntity();
        topic.setRoomId(topicDto.getRoomId());
        topic.setTopicName(topicDto.getTopicName());
        topicRepository.save(topic);
    }
    @Override
    public List<TopicEntity> getTopicsByRoomId(String roomId) {
        return topicRepository.findByRoomId(roomId);
    }
}
