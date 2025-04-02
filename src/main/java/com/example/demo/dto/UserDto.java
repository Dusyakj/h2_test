package com.example.demo.dto;

import com.example.demo.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {

    private Long id;
    private String login;
    private String password;
    private Role role;
}
