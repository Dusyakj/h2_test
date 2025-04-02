package com.example.demo.service;

import com.example.demo.dto.TopicDto;
import com.example.demo.entity.Topic;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private TopicService topicService;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTopic() {
        Topic topic = new Topic();
        topic.setId(1L);
        topic.setTitle("Test Topic");
        topic.setText("Test Text");

        when(topicRepository.findById(1L)).thenReturn(java.util.Optional.of(topic));

        TopicDto result = topicService.getTopic(1L);

        assertEquals(topic.getId(), result.getId());
        assertEquals(topic.getTitle(), result.getTitle());
        assertEquals(topic.getText(), result.getText());
    }
}