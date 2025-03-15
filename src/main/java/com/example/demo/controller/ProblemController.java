package com.example.demo.controller;

import com.example.demo.entity.Problem;
import com.example.demo.service.ProblemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {

    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Problem> getProblem(@PathVariable long id) {
        Optional<Problem> problem = problemService.getProblem(id);
        return problem.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProblem(@PathVariable long id) {
        problemService.deleteProblem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
