package com.example.demo.controller;

import com.example.demo.dto.StudentCreatedDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddStudent() {
        StudentCreatedDto studentCreatedDto = new StudentCreatedDto();
        studentCreatedDto.setFirstName("John");
        studentCreatedDto.setLastName("Doe");
        studentCreatedDto.setLogin("johndoe");
        studentCreatedDto.setPhoneNumber("1234567890");

        StudentDto studentDto = new StudentDto();
        studentDto.setId(1L);
        studentDto.setFirstName("John");
        studentDto.setLastName("Doe");
        studentDto.setLogin("johndoe");
        studentDto.setPhoneNumber("1234567890");

        when(studentService.createStudent(any(StudentCreatedDto.class))).thenReturn(studentDto);

        ResponseEntity<StudentDto> response = studentController.addPerson(studentCreatedDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(studentDto, response.getBody());
    }

    @Test
    void testGetStudent() {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(1L);
        studentDto.setFirstName("Ivan");
        studentDto.setLastName("Pupkin");
        studentDto.setLogin("IP");
        studentDto.setPhoneNumber("88005553535");

        when(studentService.getStudent(1L)).thenReturn(studentDto);

        ResponseEntity<StudentDto> response = studentController.getStudent(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentDto, response.getBody());
    }

    @Test
    void testDeleteStudent() {
        ResponseEntity<Void> response = studentController.deleteStudent(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}