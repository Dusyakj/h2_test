package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String title;
    @NotBlank
    private String description;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "course")
    private Set<Topic> topics = new HashSet<>();

    public void addTopic(Topic topic) {
        topics.add(topic);
        topic.setCourse(this);
    }

    public void addPerson(Student student) {
        students.add(student);
        student.getCourses().add(this);
    }

    public void deleteStudent(Student student) {
        students.remove(student);
        student.getCourses().remove(this);
    }
}
