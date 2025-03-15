package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.entity.Problem;
import com.example.demo.repository.ProblemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProblemService {

    private final ProblemRepository problemRepository;

    public ProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    public Optional<Problem> getProblem(long id) {
        return problemRepository.findById(id);
    }

    @Transactional
    public void deleteProblem(Long id) {
        Problem problem = problemRepository.findById(id).orElse(null);
        if (problem != null) {
            for (Student student : problem.getStudents()) {
                student.getProblems().remove(problem);
            }
//            problem.getTopic().getProblems().remove(problem);
            problemRepository.delete(problem);
        }
    }
}