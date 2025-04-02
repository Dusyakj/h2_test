package com.example.demo.controller;

import com.example.demo.dto.LinkProblemDto;
import com.example.demo.dto.StudentCreateDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.AuthService;
import com.example.demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final AuthService authService;

    public StudentController(StudentService studentService, AuthService authService) {
        this.studentService = studentService;
        this.authService = authService;
    }

    @PostMapping()
    public ResponseEntity<UserDto> addPerson(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody StudentCreateDto studentCreateDto) {
        authService.checkPermission(token);
        UserDto userDto = studentService.createStudent(token, studentCreateDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PatchMapping()
    public ResponseEntity<Void> addProblemToPerson(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody LinkProblemDto linkProblemDto) {
        authService.checkPermission(token);
        studentService.addProblemToStudent(linkProblemDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudent(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id) {
        authService.checkPermission(token);
        StudentDto student = studentService.getStudent(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id) {
        authService.checkPermission(token);
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
