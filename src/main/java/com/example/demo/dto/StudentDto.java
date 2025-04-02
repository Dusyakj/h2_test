package com.example.demo.dto;

import com.example.demo.entity.Problem;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class StudentDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String phoneNumber;
    private Set<Problem> solvedProblems;
}
