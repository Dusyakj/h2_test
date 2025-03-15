package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "problem")
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany(mappedBy = "problems")
    @JsonIgnore
    private Set<Person> persons = new HashSet<>();

    @ManyToMany(mappedBy = "problems")
    @JsonIgnore
    private Set<Topic> topics = new HashSet<>();
}