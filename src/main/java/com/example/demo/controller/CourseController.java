package com.example.demo.controller;

import com.example.demo.dto.CourseCreateDto;
import com.example.demo.dto.CourseDto;
import com.example.demo.dto.TopicCreateDto;
import com.example.demo.dto.TopicDto;
import com.example.demo.service.AuthService;
import com.example.demo.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final AuthService authService;

    public CourseController(CourseService courseService, AuthService authService) {
        this.courseService = courseService;
        this.authService = authService;
    }

    @PostMapping()
    public ResponseEntity<CourseDto> addCourse(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody CourseCreateDto courseCreateDto) {
        authService.checkPermission(token);
        CourseDto course = courseService.createCourse(courseCreateDto);
        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourse(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id) {
        authService.getUserByToken(token);
        CourseDto course = courseService.getCourse(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id) {
        authService.checkPermission(token);
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{courseId}/enroll/{studentId}")
    public ResponseEntity<Void> addStudentToCourse(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long courseId, @PathVariable Long studentId) {
        authService.checkPermission(token);
        courseService.addStudentToCourse(courseId, studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{courseId}/unenroll/{studentId}")
    public ResponseEntity<Void> deleteStudentFromCourse(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long courseId, @PathVariable Long studentId) {
        authService.checkPermission(token);
        courseService.deleteStudentFromCourse(courseId, studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/topics")
    public ResponseEntity<TopicDto> addTopic(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id, @RequestBody TopicCreateDto topicCreateDto) {
        authService.checkPermission(token);
        TopicDto topic = courseService.createTopic(id, topicCreateDto);
        return new ResponseEntity<>(topic, HttpStatus.CREATED);
    }

}
