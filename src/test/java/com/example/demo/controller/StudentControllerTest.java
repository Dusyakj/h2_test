package com.example.demo.controller;

import com.example.demo.dto.StudentCreateDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.dto.UserCreateDto;
import com.example.demo.dto.UserDto;
import com.example.demo.enums.Role;
import com.example.demo.service.AuthService;
import com.example.demo.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private StudentController studentController;

    String adminToken = Base64.getEncoder().encodeToString("admin:admin".getBytes());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddStudent() {
        StudentCreateDto studentCreateDto = new StudentCreateDto();
        studentCreateDto.setFirstName("John");
        studentCreateDto.setLastName("Doe");
        studentCreateDto.setLogin("johndoe");
        studentCreateDto.setPhoneNumber("1234567890");

        StudentDto studentDto = new StudentDto();
        studentDto.setId(1L);
        studentDto.setFirstName("John");
        studentDto.setLastName("Doe");
        studentDto.setLogin("johndoe");
        studentDto.setPhoneNumber("1234567890");

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setLogin("johndoe");
        userDto.setRole(Role.STUDENT);
        userDto.setPassword("123456");

        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setLogin("johndoe");
        userCreateDto.setRole(Role.STUDENT);
        userCreateDto.setPassword("123456");
        when(studentService.createStudent(eq(adminToken), any(StudentCreateDto.class))).thenReturn(userDto);

        ResponseEntity<UserDto> response = studentController.addPerson(adminToken, studentCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDto, response.getBody());
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

        ResponseEntity<StudentDto> response = studentController.getStudent(adminToken, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentDto, response.getBody());
    }

    @Test
    void testDeleteStudent() {
        ResponseEntity<Void> response = studentController.deleteStudent(adminToken, 1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}