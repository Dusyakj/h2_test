package com.example.demo.service;

import com.example.demo.dto.ProblemCreatedDto;
import com.example.demo.dto.ProblemDto;
import com.example.demo.dto.TopicDto;
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

    public TopicDto getTopic(Long topicId) {
        return convertTopicToDto(topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found")));
    }

    @Transactional
    public ProblemDto createProblem(Long topicId, ProblemCreatedDto problemCreatedDto) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found"));

        Problem problem = new Problem();
        problem.setTitle(problemCreatedDto.getTitle());
        problem.setDescription(problemCreatedDto.getDescription());

        topic.addProblem(problem);
        return convertProblemToDto(problemRepository.save(problem));
    }

    public void deleteTopic(Long topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found"));
        for (Problem problem : topic.getProblems()) {
            problem.setTopic(null);
        }
        topic.getCourse().getTopics().remove(topic);
        topicRepository.delete(topic);
    }

    private TopicDto convertTopicToDto(Topic topic) {
        TopicDto topicDto = new TopicDto();
        topicDto.setId(topic.getId());
        topicDto.setTitle(topic.getTitle());
        topicDto.setText(topic.getText());
        topicDto.setProblems(topic.getProblems());
        return topicDto;
    }

    private ProblemDto convertProblemToDto(Problem problem) {
        ProblemDto problemDto = new ProblemDto();
        problemDto.setId(problem.getId());
        problemDto.setTitle(problem.getTitle());
        problemDto.setDescription(problem.getDescription());
        return problemDto;
    }

}
