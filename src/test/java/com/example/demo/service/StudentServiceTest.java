package com.example.demo.service;

import com.example.demo.dto.StudentCreateDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    String adminToken = Base64.getEncoder().encodeToString("admin:admin".getBytes());

    @Test
    void testCreateStudent() {
        StudentCreateDto studentCreateDto = new StudentCreateDto();
        studentCreateDto.setFirstName("Ivan");
        studentCreateDto.setLastName("Pupkin");
        studentCreateDto.setLogin("A");
        studentCreateDto.setPhoneNumber("8800553535");

        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Ivan");
        student.setLastName("Pupkin");
        student.setLogin("A");
        student.setPhoneNumber("8800553535");


        UserDto user = studentService.createStudent(adminToken, studentCreateDto);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        StudentDto result = studentService.getStudent(1L);

        assertEquals(student.getId(), result.getId());
        assertEquals(student.getFirstName(), result.getFirstName());
        assertEquals(student.getLastName(), result.getLastName());
        assertEquals(student.getLogin(), result.getLogin());
        assertEquals(student.getPhoneNumber(), result.getPhoneNumber());
    }

    @Test
    void testGetStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Ivan");
        student.setLastName("Pupkin");
        student.setLogin("A");
        student.setPhoneNumber("8800553535");

        when(studentRepository.findById(1L)).thenReturn(java.util.Optional.of(student));

        StudentDto result = studentService.getStudent(1L);

        assertEquals(student.getId(), result.getId());
        assertEquals(student.getFirstName(), result.getFirstName());
        assertEquals(student.getLastName(), result.getLastName());
        assertEquals(student.getLogin(), result.getLogin());
        assertEquals(student.getPhoneNumber(), result.getPhoneNumber());
    }
}