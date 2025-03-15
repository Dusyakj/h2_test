package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkProblemDto {

    @NotNull
    private long studentId;
    @NotNull
    private long problemId;
}
