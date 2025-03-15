package com.example.demo.controller;

import com.example.demo.dto.ProblemCreatedDto;
import com.example.demo.dto.ProblemDto;
import com.example.demo.dto.TopicDto;
import com.example.demo.entity.Problem;
import com.example.demo.entity.Topic;
import com.example.demo.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getTopic(@PathVariable long id) {
        TopicDto topic = topicService.getTopic(id);
        return new ResponseEntity<>(topic, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable long id) {
        topicService.deleteTopic(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/problems")
    public ResponseEntity<ProblemDto> addProblem(@PathVariable long id, @RequestBody ProblemCreatedDto problemCreatedDto) {
        ProblemDto problem = topicService.createProblem(id, problemCreatedDto);
        return new ResponseEntity<>(problem, HttpStatus.CREATED);
    }
}
