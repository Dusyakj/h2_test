package com.example.demo.controller;

import com.example.demo.dto.LinkProblemDto;
import com.example.demo.dto.StudentCreatedDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping()
    public ResponseEntity<StudentDto> addPerson(@Valid @RequestBody StudentCreatedDto studentCreatedDto) {
        StudentDto student = studentService.createStudent(studentCreatedDto);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @PatchMapping()
    public ResponseEntity<Void> addProblemToPerson(@Valid @RequestBody LinkProblemDto linkProblemDto) {
        studentService.addProblemToStudent(linkProblemDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable Long id) {
        StudentDto student = studentService.getStudent(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
