package com.example.retrospect.topic.repository;

import com.example.retrospect.topic.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<TopicEntity, Long> {
}
