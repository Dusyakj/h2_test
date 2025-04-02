package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.User;
import com.example.demo.enums.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;
import com.example.demo.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    String adminToken = Base64.getEncoder().encodeToString("admin:admin".getBytes());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignUp() {

        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setLogin("mentor");
        userCreateDto.setPassword("mentor");
        userCreateDto.setRole(Role.MENTOR);

        UserDto userDto = new UserDto();
        userDto.setId(2L);
        userDto.setPassword("mentor");
        userDto.setRole(Role.MENTOR);

        when(authService.createUserByAdmin(eq(adminToken), any(UserCreateDto.class))).thenReturn(userDto);

        ResponseEntity<UserDto> response = authController.signUp(adminToken, userCreateDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void testSignIn() {

        SignInDto signInDto = new SignInDto();
        signInDto.setPassword("admin");
        signInDto.setLogin("admin");

        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(adminToken);


        when(authService.signIn(any(SignInDto.class))).thenReturn(tokenDto);


        ResponseEntity<TokenDto> response = authController.signIn(signInDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tokenDto, response.getBody());
    }
}
