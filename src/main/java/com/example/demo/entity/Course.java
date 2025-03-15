package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Person> persons = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "course")
    private Set<Topic> topics = new HashSet<>();

    public void addTopic(Topic topic) {
        topics.add(topic);
        topic.setCourse(this);
    }

    public void addPerson(Person person) {
        persons.add(person);
        person.getCourses().add(this);
    }
}
