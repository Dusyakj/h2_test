package com.example.demo.controller;

import com.example.demo.dto.CourseCreatedDto;
import com.example.demo.dto.CourseDto;
import com.example.demo.dto.TopicCreatedDto;
import com.example.demo.dto.TopicDto;
import com.example.demo.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping()
    public ResponseEntity<CourseDto> addCourse(@Valid @RequestBody CourseCreatedDto courseCreatedDto) {
        CourseDto course = courseService.createCourse(courseCreatedDto);
        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable Long id) {
        CourseDto course = courseService.getCourse(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{courseId}/enroll/{studentId}")
    public ResponseEntity<Void> addStudentToCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseService.addStudentToCourse(courseId, studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{courseId}/unenroll/{studentId}")
    public ResponseEntity<Void> deleteStudentFromCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseService.deleteStudentFromCourse(courseId, studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/topics")
    public ResponseEntity<TopicDto> addTopic(@PathVariable Long id, @RequestBody TopicCreatedDto topicCreatedDto) {
        TopicDto topic = courseService.createTopic(id, topicCreatedDto);
        return new ResponseEntity<>(topic, HttpStatus.CREATED);
    }

}
