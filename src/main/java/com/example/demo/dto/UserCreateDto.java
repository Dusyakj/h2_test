package com.example.demo.dto;

import com.example.demo.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {

    @NotBlank
    private String login;

    @NotNull
    @Size(min = 5)
    private String password;

    @NotNull
    private Role role;
}
