package com.example.demo.service;

import com.example.demo.dto.CourseDto;
import com.example.demo.dto.TopicDto;
import com.example.demo.entity.Course;
import com.example.demo.entity.Person;
import com.example.demo.entity.Topic;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final PersonRepository personRepository;
    private final TopicRepository topicRepository;

    public CourseService(CourseRepository courseRepository, PersonRepository personRepository, TopicRepository topicRepository) {
        this.courseRepository = courseRepository;
        this.personRepository = personRepository;
        this.topicRepository = topicRepository;
    }

    public Topic createTopic(long id, TopicDto topicDto) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        Topic topic = new Topic();
        topic.setText(topicDto.getText());
        topic.setTitle(topicDto.getTitle());

        course.addTopic(topic);
        return topicRepository.save(topic);
    }

    public Course createCourse(CourseDto courseDto) {
        Course course = new Course();
        course.setTitle(courseDto.getTitle());
        return courseRepository.save(course);
    }

    public Optional<Course> getCourse(long id) {
        return courseRepository.findById(id);
    }

    public void addStudentToCourse(long courseId, long personId) {
        Person person = personRepository.findById(personId).orElseThrow(() -> new RuntimeException("Person not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        course.addPerson(person);
        courseRepository.save(course);
    }

    public void deleteCourse(long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Topic not found"));
        for (Person person : course.getPersons()) {
            person.getCourses().remove(course);
        }

        for (Topic topic : course.getTopics()) {
            topic.setCourse(null);
        }
        courseRepository.delete(course);
    }
}
