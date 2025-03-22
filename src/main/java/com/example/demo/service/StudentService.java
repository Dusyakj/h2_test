package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.Course;
import com.example.demo.entity.Problem;
import com.example.demo.entity.Student;
import com.example.demo.enums.Role;
import com.example.demo.exception.NotFoundRuntimeException;
import com.example.demo.repository.ProblemRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final ProblemRepository problemRepository;

    private final AuthService authService;

    public StudentService(StudentRepository studentRepository, ProblemRepository problemRepository, AuthService authService) {
        this.studentRepository = studentRepository;
        this.problemRepository = problemRepository;
        this.authService = authService;
    }

    @Transactional
    public UserDto createStudent(String token, StudentCreateDto studentCreateDto) {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setLogin(studentCreateDto.getLogin());
        userCreateDto.setRole(Role.STUDENT);
        userCreateDto.setPassword(authService.generatePassword());
        UserDto userDto = authService.creteUser(userCreateDto);

        Student student = new Student();
        student.setFirstName(studentCreateDto.getFirstName());
        student.setLogin(studentCreateDto.getLogin());
        student.setLastName(studentCreateDto.getLastName());
        student.setPhoneNumber(studentCreateDto.getPhoneNumber());
        studentRepository.save(student);
        return userDto;
    }

    public StudentDto getStudent(Long studentId) {
        return convertStudentToDto(studentRepository.findById(studentId).orElseThrow(() -> new NotFoundRuntimeException("Student not found")));
    }

    @Transactional
    public void addProblemToStudent(LinkProblemDto linkProblemDto) {
        Student student = studentRepository.findById(linkProblemDto.getStudentId()).orElseThrow(() -> new NotFoundRuntimeException("Student not found"));
        Problem problem = problemRepository.findById(linkProblemDto.getProblemId()).orElseThrow(() -> new NotFoundRuntimeException("Problem not found"));

        student.addProblem(problem);
        studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundRuntimeException("Student not found"));
        for (Problem problem : student.getProblems()) {
            problem.getStudents().remove(student);
        }

        for (Course course : student.getCourses()) {
            course.getStudents().remove(student);
        }

        studentRepository.delete(student);
        authService.deleteUserByLogin(student.getLogin());
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