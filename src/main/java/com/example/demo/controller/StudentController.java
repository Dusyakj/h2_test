package com.example.demo.controller;

import com.example.demo.dto.LinkProblemDto;
import com.example.demo.dto.StudentCreatedDto;
import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping()
    public ResponseEntity<Student> addPerson(@Valid @RequestBody StudentCreatedDto studentCreatedDto) {
        Student student = studentService.createPerson(studentCreatedDto);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @PatchMapping()
    public ResponseEntity<Void> addProblemToPerson(@Valid @RequestBody LinkProblemDto linkProblemDto) {
        studentService.addProblemToPerson(linkProblemDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        Optional<Student> person = studentService.getPerson(id);
        return person.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
