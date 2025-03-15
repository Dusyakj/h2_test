package com.example.demo.dto;

import com.example.demo.entity.Student;
import com.example.demo.entity.Topic;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CourseDto {

    private Long id;
    private String title;
    private String description;

    private Set<Topic> topics;
    private Set<Student> students;
}
