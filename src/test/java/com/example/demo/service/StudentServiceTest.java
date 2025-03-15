package com.example.demo.service;

import com.example.demo.dto.StudentCreatedDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStudent() {
        StudentCreatedDto studentCreatedDto = new StudentCreatedDto();
        studentCreatedDto.setFirstName("Ivan");
        studentCreatedDto.setLastName("Pupkin");
        studentCreatedDto.setLogin("A");
        studentCreatedDto.setPhoneNumber("8800553535");

        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Ivan");
        student.setLastName("Pupkin");
        student.setLogin("A");
        student.setPhoneNumber("8800553535");

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentDto result = studentService.createStudent(studentCreatedDto);

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