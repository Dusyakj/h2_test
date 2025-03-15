package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotBlank
    private String title;
    @NotBlank
    private String text;

    @ToString.Exclude
    @OneToMany(mappedBy = "topic")
    private Set<Problem> problems = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @JsonIgnore
    private Course course;

    public void addProblem(Problem problem) {
        problems.add(problem);
        problem.setTopic(this);
    }
}
