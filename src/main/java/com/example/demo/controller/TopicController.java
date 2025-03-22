package com.example.demo.controller;

import com.example.demo.dto.ProblemCreateDto;
import com.example.demo.dto.ProblemDto;
import com.example.demo.dto.TopicDto;
import com.example.demo.service.AuthService;
import com.example.demo.service.TopicService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;
    private final AuthService authService;

    public TopicController(TopicService topicService, AuthService authService) {
        this.topicService = topicService;
        this.authService = authService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getTopic(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable long id) {
        authService.getUserByToken(token);
        TopicDto topic = topicService.getTopic(id);
        return new ResponseEntity<>(topic, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable long id) {
        authService.checkPermission(token);
        topicService.deleteTopic(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/problems")
    public ResponseEntity<ProblemDto> addProblem(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable long id, @RequestBody ProblemCreateDto problemCreateDto) {
        authService.checkPermission(token);
        ProblemDto problem = topicService.createProblem(id, problemCreateDto);
        return new ResponseEntity<>(problem, HttpStatus.CREATED);
    }
}
