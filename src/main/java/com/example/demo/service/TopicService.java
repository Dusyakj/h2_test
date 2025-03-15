package com.example.demo.service;

import com.example.demo.dto.TopicDto;
import com.example.demo.entity.Course;
import com.example.demo.entity.Problem;
import com.example.demo.entity.Topic;
import com.example.demo.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Optional<Topic> getTopic(long id) {
        return topicRepository.findById(id);
    }

    public void deleteTopic(long id) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Topic not found"));
        for (Problem problem : topic.getProblems()) {
            problem.setTopic(null);
        }
        topicRepository.delete(topic);
    }

}
