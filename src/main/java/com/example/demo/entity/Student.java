package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;
    @NotBlank
    private String login;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phoneNumber;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "student_problem",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "problem_id")
    )
    private Set<Problem> problems = new HashSet<>();

    @ToString.Exclude
    @ManyToMany(mappedBy = "students")
    @JsonIgnore
    private Set<Course> courses = new HashSet<>();

    public void addProblem(Problem problem) {
        problems.add(problem);
        problem.getStudents().add(this);
    }
}