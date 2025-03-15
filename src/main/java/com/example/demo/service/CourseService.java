package com.example.demo.service;

import com.example.demo.dto.CourseCreatedDto;
import com.example.demo.dto.CourseDto;
import com.example.demo.dto.TopicCreatedDto;
import com.example.demo.dto.TopicDto;
import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import com.example.demo.entity.Topic;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TopicRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TopicRepository topicRepository;

    private final TopicService topicService;

    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository, TopicRepository topicRepository, TopicService topicService) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.topicRepository = topicRepository;
        this.topicService = topicService;
    }

    public TopicDto createTopic(Long courseId, TopicCreatedDto topicCreatedDto) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        Topic topic = new Topic();
        topic.setText(topicCreatedDto.getText());
        topic.setTitle(topicCreatedDto.getTitle());

        course.addTopic(topic);
        return convertTopicToDto((topicRepository.save(topic)));
    }

    public CourseDto createCourse(CourseCreatedDto courseCreatedDto) {
        Course course = new Course();
        course.setTitle(courseCreatedDto.getTitle());
        course.setDescription(courseCreatedDto.getDescription());
        return convertCourseToDto(courseRepository.save(course));
    }

    public CourseDto getCourse(Long courseId) {
        return convertCourseToDto(courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found")));
    }

    public void addStudentToCourse(Long courseId, Long personId) {
        Student student = studentRepository.findById(personId).orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        course.addStudent(student);
        courseRepository.save(course);
    }

    public void deleteStudentFromCourse(Long courseId, Long personId) {
        Student student = studentRepository.findById(personId).orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        course.deleteStudent(student);
        courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        for (Student student : course.getStudents()) {
            student.getCourses().remove(course);
        }

        for (Topic topic : course.getTopics()) {
            topicService.deleteTopic(topic.getId());
        }
        courseRepository.delete(course);
    }

    private CourseDto convertCourseToDto(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setTitle(course.getTitle());
        courseDto.setDescription(course.getDescription());
        courseDto.setTopics(course.getTopics());
        courseDto.setStudents(course.getStudents());
        return courseDto;
    }

    private TopicDto convertTopicToDto(Topic topic) {
        TopicDto topicDto = new TopicDto();
        topicDto.setId(topic.getId());
        topicDto.setTitle(topic.getTitle());
        topicDto.setText(topic.getText());
        topicDto.setProblems(topic.getProblems());
        return topicDto;
    }

}
