package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourseCreatedDto {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
