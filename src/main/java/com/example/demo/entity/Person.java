package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "person_problem",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "problem_id")
    )
    private Set<Problem> problems = new HashSet<>();

    @ToString.Exclude
    @ManyToMany(mappedBy = "persons")
    @JsonIgnore
    private Set<Course> courses = new HashSet<>();

    public void addProblem(Problem problem) {
        problems.add(problem);
        problem.getPersons().add(this);
    }
}