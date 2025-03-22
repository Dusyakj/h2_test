package com.example.demo.controller;

import com.example.demo.dto.ProblemDto;
import com.example.demo.service.AuthService;
import com.example.demo.service.ProblemService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {

    private final ProblemService problemService;
    private final AuthService authService;

    public ProblemController(ProblemService problemService, AuthService authService) {
        this.problemService = problemService;
        this.authService = authService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProblemDto> getProblem(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable long id) {
        authService.getUserByToken(token);
        ProblemDto problem = problemService.getProblem(id);
        return new ResponseEntity<>(problem, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProblem(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable long id) {
        authService.checkPermission(token);
        problemService.deleteProblem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
