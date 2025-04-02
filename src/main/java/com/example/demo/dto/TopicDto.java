package com.example.demo.dto;

import com.example.demo.entity.Problem;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TopicDto {

    private Long id;
    private String title;
    private String text;
    private Set<Problem> problems;
}
