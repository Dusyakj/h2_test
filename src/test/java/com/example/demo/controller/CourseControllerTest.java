package com.example.demo.controller;

import com.example.demo.dto.CourseCreateDto;
import com.example.demo.dto.CourseDto;
import com.example.demo.service.AuthService;
import com.example.demo.service.CourseService;
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
import static org.mockito.Mockito.when;

class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private CourseController courseController;

    String adminToken = Base64.getEncoder().encodeToString("admin:admin".getBytes());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCourse() {
        CourseCreateDto courseCreateDto = new CourseCreateDto();
        courseCreateDto.setTitle("Test Course");
        courseCreateDto.setDescription("Test Description");

        CourseDto courseDto = new CourseDto();
        courseDto.setId(1L);
        courseDto.setTitle("Test Course");
        courseDto.setDescription("Test Description");

        when(courseService.createCourse(any(CourseCreateDto.class))).thenReturn(courseDto);


        ResponseEntity<CourseDto> response = courseController.addCourse(adminToken, courseCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(courseDto, response.getBody());
    }

    @Test
    void testGetCourse() {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(1L);
        courseDto.setTitle("Test Course");
        courseDto.setDescription("Test Description");

        when(courseService.getCourse(1L)).thenReturn(courseDto);

        ResponseEntity<CourseDto> response = courseController.getCourse(adminToken, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courseDto, response.getBody());
    }

    @Test
    void testDeleteCourse() {
        ResponseEntity<Void> response = courseController.deleteCourse(adminToken, 1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}