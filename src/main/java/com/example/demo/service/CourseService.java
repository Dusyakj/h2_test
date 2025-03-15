package com.example.demo.service;

import com.example.demo.dto.CourseCreatedDto;
import com.example.demo.dto.TopicCreatedDto;
import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import com.example.demo.entity.Topic;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TopicRepository topicRepository;

    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository, TopicRepository topicRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.topicRepository = topicRepository;
    }

    public Topic createTopic(long id, TopicCreatedDto topicCreatedDto) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        Topic topic = new Topic();
        topic.setText(topicCreatedDto.getText());
        topic.setTitle(topicCreatedDto.getTitle());

        course.addTopic(topic);
        return topicRepository.save(topic);
    }

    public Course createCourse(CourseCreatedDto courseCreatedDto) {
        Course course = new Course();
        course.setTitle(courseCreatedDto.getTitle());
        course.setDescription(courseCreatedDto.getDescription());
        return courseRepository.save(course);
    }

    public Optional<Course> getCourse(long id) {
        return courseRepository.findById(id);
    }

    public void addStudentToCourse(long courseId, long personId) {
        Student student = studentRepository.findById(personId).orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        course.addPerson(student);
        courseRepository.save(course);
    }

    public void deleteStudentFromCourse(long courseId, long personId) {
        Student student = studentRepository.findById(personId).orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        course.deleteStudent(student);
        courseRepository.save(course);
    }

    public void deleteCourse(long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        for (Student student : course.getStudents()) {
            student.getCourses().remove(course);
        }

        for (Topic topic : course.getTopics()) {
            topic.setCourse(null);
        }
        courseRepository.delete(course);
    }
}
