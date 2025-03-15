package com.example.demo.controller;

import com.example.demo.dto.CourseCreatedDto;
import com.example.demo.dto.CourseDto;
import com.example.demo.dto.TopicCreatedDto;
import com.example.demo.dto.TopicDto;
import com.example.demo.entity.Course;
import com.example.demo.entity.Topic;
import com.example.demo.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @PostMapping("/{courseId}/enroll/{personId}")
    public ResponseEntity<Void> addPersonToCourse(@PathVariable Long courseId, @PathVariable Long personId) {
        courseService.addStudentToCourse(courseId, personId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{courseId}/unenroll/{personId}")
    public ResponseEntity<Void> deletePersonFromCourse(@PathVariable Long courseId, @PathVariable Long personId) {
        courseService.deleteStudentFromCourse(courseId, personId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/topics")
    public ResponseEntity<TopicDto> addProblem(@PathVariable Long id, @RequestBody TopicCreatedDto topicCreatedDto) {
        TopicDto topic = courseService.createTopic(id, topicCreatedDto);
        return new ResponseEntity<>(topic, HttpStatus.CREATED);
    }

}
