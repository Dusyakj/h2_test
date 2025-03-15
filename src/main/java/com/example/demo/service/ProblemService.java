package com.example.demo.service;

import com.example.demo.dto.ProblemDto;
import com.example.demo.entity.Problem;
import com.example.demo.entity.Student;
import com.example.demo.entity.Topic;
import com.example.demo.repository.ProblemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProblemService {

    private final ProblemRepository problemRepository;

    public ProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    public ProblemDto getProblem(Long id) {
        return convertProblemToDto(problemRepository.findById(id).orElseThrow(() -> new RuntimeException("Problem not found")));
    }

    @Transactional
    public void deleteProblem(Long id) {
        Problem problem = problemRepository.findById(id).orElse(null);
        if (problem != null) {
            for (Student student : problem.getStudents()) {
                student.getProblems().remove(problem);
            }

            Topic topic = problem.getTopic();
            if (topic != null) {
                topic.getProblems().remove(problem);
            }
            problemRepository.delete(problem);
        }
    }

    private ProblemDto convertProblemToDto(Problem problem) {
        ProblemDto problemDto = new ProblemDto();
        problemDto.setId(problem.getId());
        problemDto.setTitle(problem.getTitle());
        problemDto.setDescription(problem.getDescription());
        return problemDto;
    }
}