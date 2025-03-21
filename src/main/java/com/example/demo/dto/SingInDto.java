package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingInDto {

    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
