package com.example.demo.controller;

import com.example.demo.dto.SingInDto;
import com.example.demo.dto.TokenDto;
import com.example.demo.dto.UserCreateDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/singUp")
    public ResponseEntity<UserDto> createUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody UserCreateDto userCreateDto) {
        UserDto user = authService.createUserByAdmin(token, userCreateDto);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @PostMapping("/singIn")
    public ResponseEntity<TokenDto> singIn(@Valid @RequestBody SingInDto singInDto) {
        TokenDto token = authService.singIn(singInDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
