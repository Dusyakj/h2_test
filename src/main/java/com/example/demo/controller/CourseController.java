package com.example.demo.controller;

import com.example.demo.dto.CourseCreatedDto;
import com.example.demo.dto.TopicCreatedDto;
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
    public ResponseEntity<Course> addCourse(@Valid @RequestBody CourseCreatedDto courseCreatedDto) {
        Course course = courseService.createCourse(courseCreatedDto);
        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable long id) {
        Optional<Course> course = courseService.getCourse(id);
        return course.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable long id) {
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

    @PostMapping("/{id}/topic")
    public ResponseEntity<Topic> addProblem(@PathVariable long id, @RequestBody TopicCreatedDto topicCreatedDto) {
        Topic topic = courseService.createTopic(id, topicCreatedDto);
        return new ResponseEntity<>(topic, HttpStatus.CREATED);
    }

}
