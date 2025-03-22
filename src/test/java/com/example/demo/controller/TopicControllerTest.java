package com.example.demo.controller;

import com.example.demo.dto.TopicCreateDto;
import com.example.demo.dto.TopicDto;
import com.example.demo.service.AuthService;
import com.example.demo.service.CourseService;
import com.example.demo.service.TopicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class TopicControllerTest {

    @Mock
    private TopicService topicService;

    @Mock
    private CourseService courseService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private TopicController topicController;

    @InjectMocks
    private CourseController courseController;

    String adminToken = Base64.getEncoder().encodeToString("admin:admin".getBytes());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTopic() {
        TopicCreateDto topicCreateDto = new TopicCreateDto();
        topicCreateDto.setTitle("Test Topic");
        topicCreateDto.setText("Test Text");

        TopicDto topicDto = new TopicDto();
        topicDto.setId(1L);
        topicDto.setTitle("Test Topic");
        topicDto.setText("Test Text");


        when(courseService.createTopic(anyLong(), any(TopicCreateDto.class))).thenReturn(topicDto);

        ResponseEntity<TopicDto> response = courseController.addTopic(adminToken, 1L, topicCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(topicDto, response.getBody());
    }

    @Test
    void testGetTopic() {
        TopicDto topicDto = new TopicDto();
        topicDto.setId(1L);
        topicDto.setTitle("Test Topic");
        topicDto.setText("Test Text");

        when(topicService.getTopic(1L)).thenReturn(topicDto);

        ResponseEntity<TopicDto> response = topicController.getTopic(adminToken, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(topicDto, response.getBody());
    }

    @Test
    void testDeleteTopic() {
        ResponseEntity<Void> response = topicController.deleteTopic(adminToken, 1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}