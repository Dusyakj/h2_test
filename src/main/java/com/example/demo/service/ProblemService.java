package com.example.demo.service;

import com.example.demo.dto.ProblemDto;
import com.example.demo.entity.Person;
import com.example.demo.entity.Problem;
import com.example.demo.entity.Topic;
import com.example.demo.repository.ProblemRepository;
import com.example.demo.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final TopicRepository topicRepository;

    public ProblemService(ProblemRepository problemRepository, TopicRepository topicRepository) {
        this.problemRepository = problemRepository;
        this.topicRepository = topicRepository;
    }

    @Transactional
    public Problem createProblem(ProblemDto problemDto) {
        Topic topic = topicRepository.findById(problemDto.getTopicId()).orElseThrow(() -> new RuntimeException("Topic not found"));

        Problem problem = new Problem();
        problem.setTitle(problemDto.getTitle());

        topic.addProblem(problem);
        return problemRepository.save(problem);
    }

    @Transactional
    public void deleteProblem(Long id) {
        Problem problem = problemRepository.findById(id).orElse(null);
        if (problem != null) {
            for (Person person: problem.getPersons()) {
                person.getProblems().remove(problem);
            }
//            problem.getTopic().getProblems().remove(problem);
            problemRepository.delete(problem);
        }
    }
}