package com.example.demo.controller;
// ... other imports ...

import com.example.demo.dto.CourseDto;
import com.example.demo.dto.PersonDto;
import com.example.demo.dto.ProblemDto;
import com.example.demo.dto.TopicDto;
import com.example.demo.entity.Course;
import com.example.demo.entity.Person;
import com.example.demo.entity.Problem;
import com.example.demo.entity.Topic;
import com.example.demo.service.CourseService;
import com.example.demo.service.PersonService;
import com.example.demo.service.ProblemService;
import com.example.demo.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TestController {

    private final PersonService personService;
    private final ProblemService problemService;
    private final TopicService topicService;
    private final CourseService courseService;

    public TestController(PersonService personService, ProblemService problemService, TopicService topicService, CourseService courseService) {
        this.personService = personService;
        this.problemService = problemService;
        this.topicService = topicService;
        this.courseService = courseService;
    }

    @PostMapping("/person")
    public ResponseEntity<Person> addPerson(@RequestBody PersonDto personDto) {
        Person person = personService.createPerson(personDto);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @PostMapping("/problem")
    public ResponseEntity<Problem> addProblem(@RequestBody ProblemDto problemDto) {
        Problem problem = problemService.createProblem(problemDto);
        return new ResponseEntity<>(problem, HttpStatus.CREATED);
    }

    @PostMapping("/course/{id}/topic")
    public ResponseEntity<Topic> addProblem(@PathVariable long id, @RequestBody TopicDto topicDto) {
        Topic topic = courseService.createTopic(id, topicDto);
        return new ResponseEntity<>(topic, HttpStatus.CREATED);
    }

    @PostMapping("/course")
    public ResponseEntity<Course> addCourse(@RequestBody CourseDto courseDto) {
        Course course = courseService.createCourse(courseDto);
        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    @PostMapping("/courses/{courseId}/enroll/{personId}")
    public ResponseEntity<Void> addPersonToCourse(@PathVariable Long courseId, @PathVariable Long personId) {
        courseService.addStudentToCourse(courseId, personId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/person/{personId}/problem/{problemId}")
    public ResponseEntity<Void> addProblemToPerson(@PathVariable Long personId, @PathVariable Long problemId) {
        personService.addProblemToPerson(personId, problemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable long id) {
        Optional<Person> person = personService.getPerson(id);
        return person.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("topic/{id}")
    public ResponseEntity<Topic> getTopic(@PathVariable long id) {
        Optional<Topic> topic = topicService.getTopic(id);
        return topic.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable long id) {
        Optional<Course> course = courseService.getCourse(id);
        return course.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/topic/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable long id) {
        topicService.deleteTopic(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content is appropriate for successful deletion
    }

    @DeleteMapping("/problem/{id}")
    public ResponseEntity<Void> deleteProblem(@PathVariable long id) {
        problemService.deleteProblem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content is appropriate for successful deletion
    }

    @DeleteMapping("/course/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content is appropriate for successful deletion

    }
}