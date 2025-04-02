package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemCreateDto {

    @NotBlank
    @Size(max = 100)
    private String title;
    @NotBlank
    @Size(max = 3000)
    private String description;
}