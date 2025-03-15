package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicCreatedDto {

    @NotBlank
    private String title;
    @NotBlank
    private String text;
}
