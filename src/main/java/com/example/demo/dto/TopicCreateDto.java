package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicCreateDto {

    @NotBlank
    @Size(max = 100)
    private String title;
    @NotBlank
    @Size(max = 3000)
    private String text;
}
