package com.example.demo.service;

import com.example.demo.dto.LinkProblemDto;
import com.example.demo.dto.StudentCreatedDto;
import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import com.example.demo.entity.Problem;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.ProblemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final ProblemRepository problemRepository;

    public StudentService(StudentRepository studentRepository, ProblemRepository problemRepository) {
        this.studentRepository = studentRepository;
        this.problemRepository = problemRepository;
    }

    public Student createPerson(StudentCreatedDto studentCreatedDto) {
        Student student = new Student();
        student.setFirstName(studentCreatedDto.getFirstName());
        student.setLogin(studentCreatedDto.getLogin());
        student.setLastName(studentCreatedDto.getLastName());
        student.setPhoneNumber(studentCreatedDto.getPhoneNumber());
        return studentRepository.save(student);
    }

    public Optional<Student> getPerson(long id) {
        return studentRepository.findById(id);
    }

    @Transactional
    public void addProblemToPerson(LinkProblemDto linkProblemDto) {
        Student student = studentRepository.findById(linkProblemDto.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found"));
        Problem problem = problemRepository.findById(linkProblemDto.getProblemId()).orElseThrow(() -> new RuntimeException("Problem not found"));

        student.addProblem(problem);
        studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        for (Problem problem : student.getProblems()) {
            problem.getStudents().remove(student);
        }

        for (Course course : student.getCourses()) {
            course.getStudents().remove(student);
        }

        studentRepository.delete(student);
    }
}