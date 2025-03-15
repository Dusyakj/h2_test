package com.example.demo.service;

import com.example.demo.dto.PersonDto;
import com.example.demo.dto.TopicDto;
import com.example.demo.entity.Person;
import com.example.demo.entity.Topic;
import com.example.demo.repository.TopicRepository;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic createTopic(TopicDto topicDto) {
        Topic topic = new Topic();
        topic.setText(topicDto.getText());
        topic.setTitle(topicDto.getTitle());
        return topicRepository.save(topic);
    }
}
