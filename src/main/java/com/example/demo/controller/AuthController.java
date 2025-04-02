package com.example.demo.controller;

import com.example.demo.dto.SignInDto;
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

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody UserCreateDto userCreateDto) {
        UserDto user = authService.createUserByAdmin(token, userCreateDto);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @PostMapping("/signIn")
    public ResponseEntity<TokenDto> signIn(@Valid @RequestBody SignInDto signInDto) {
        TokenDto token = authService.signIn(signInDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
