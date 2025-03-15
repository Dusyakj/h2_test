package com.example.demo.controller;
// ... other imports ...
import com.example.demo.dto.PersonDto;
import com.example.demo.dto.ProblemDto;
import com.example.demo.dto.TopicDto;
import com.example.demo.entity.Person;
import com.example.demo.entity.Problem;
import com.example.demo.entity.Topic;
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

    public TestController(PersonService personService, ProblemService problemService, TopicService topicService) {
        this.personService = personService;
        this.problemService = problemService;
        this.topicService = topicService;
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

    @PostMapping("/topic")
    public ResponseEntity<Topic> addProblem(@RequestBody TopicDto topicDto) {
        Topic topic = topicService.createTopic(topicDto);
        return new ResponseEntity<>(topic, HttpStatus.CREATED);
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

    @DeleteMapping("/problem/{id}")
    public ResponseEntity<Void> deleteProblem(@PathVariable long id) {
        problemService.deleteProblem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content is appropriate for successful deletion
    }
}