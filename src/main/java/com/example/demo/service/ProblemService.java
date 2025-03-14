package com.example.demo.service;


import com.example.demo.dto.ProblemDto;
import com.example.demo.entity.Problem;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ProblemRepository;
import org.springframework.stereotype.Service;


@Service
public class ProblemService {
    private ProblemRepository problemRepository;

    private PersonRepository personRepository;

    public ProblemService(PersonRepository personRepository, ProblemRepository problemRepository) {
        this.personRepository = personRepository;
        this.problemRepository = problemRepository;
    }

    public Problem createProblem(ProblemDto problem) {
        Problem problem1 = new Problem();
        problem1.setTitle(problem.getTitle());
        return problemRepository.save(problem1);
    }

    public void deleteProblem(Long id) {
        Problem problem = problemRepository.findById(id).orElse(null);
        if (problem != null) {
            problemRepository.deleteById(id);
        }
    }
}
