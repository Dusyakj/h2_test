package com.example.demo.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicDto {

    private long id;
    @NotBlank
    private String title;
    private String text;
}
