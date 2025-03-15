package com.example.demo.service;

import com.example.demo.dto.LinkProblemDto;
import com.example.demo.dto.StudentCreatedDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Course;
import com.example.demo.entity.Problem;
import com.example.demo.entity.Student;
import com.example.demo.repository.ProblemRepository;
import com.example.demo.repository.StudentRepository;
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

    public StudentDto createStudent(StudentCreatedDto studentCreatedDto) {
        Student student = new Student();
        student.setFirstName(studentCreatedDto.getFirstName());
        student.setLogin(studentCreatedDto.getLogin());
        student.setLastName(studentCreatedDto.getLastName());
        student.setPhoneNumber(studentCreatedDto.getPhoneNumber());
        return convertStudentToDto(studentRepository.save(student));
    }

    public StudentDto getStudent(Long studentId) {
        return convertStudentToDto(studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found")));
    }

    @Transactional
    public void addProblemToStudent(LinkProblemDto linkProblemDto) {
        Student student = studentRepository.findById(linkProblemDto.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found"));
        Problem problem = problemRepository.findById(linkProblemDto.getProblemId()).orElseThrow(() -> new RuntimeException("Problem not found"));

        student.addProblem(problem);
        studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        for (Problem problem : student.getProblems()) {
            problem.getStudents().remove(student);
        }

        for (Course course : student.getCourses()) {
            course.getStudents().remove(student);
        }

        studentRepository.delete(student);
    }

    private StudentDto convertStudentToDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setLogin(student.getLogin());
        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        studentDto.setPhoneNumber(student.getPhoneNumber());
        studentDto.setSolvedProblems(student.getProblems());
        return studentDto;
    }
}