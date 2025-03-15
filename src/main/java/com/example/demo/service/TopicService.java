package com.example.demo.service;

import com.example.demo.dto.ProblemCreatedDto;
import com.example.demo.entity.Problem;
import com.example.demo.entity.Topic;
import com.example.demo.repository.ProblemRepository;
import com.example.demo.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final ProblemRepository problemRepository;


    public TopicService(TopicRepository topicRepository, ProblemRepository problemRepository) {
        this.topicRepository = topicRepository;
        this.problemRepository = problemRepository;
    }

    public Optional<Topic> getTopic(long id) {
        return topicRepository.findById(id);
    }

    @Transactional
    public Problem createProblem(long topicId, ProblemCreatedDto problemCreatedDto) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found"));

        Problem problem = new Problem();
        problem.setTitle(problemCreatedDto.getTitle());

        topic.addProblem(problem);
        return problemRepository.save(problem);
    }

    public void deleteTopic(long id) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Topic not found"));
        for (Problem problem : topic.getProblems()) {
            problem.setTopic(null);
        }
        topic.getCourse().getTopics().remove(topic);
        topicRepository.delete(topic);
    }

}
