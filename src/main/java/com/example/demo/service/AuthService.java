package com.example.demo.service;

import com.example.demo.dto.SingInDto;
import com.example.demo.dto.TokenDto;
import com.example.demo.dto.UserCreateDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.enums.Role;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDto creteUser(UserCreateDto userCreateDto) {
        User user = userRepository.findByLogin(userCreateDto.getLogin());
        if (user != null) {
            throw new RuntimeException("User already exists");
        }

        User newUser = new User();
        newUser.setLogin(userCreateDto.getLogin());
        newUser.setPassword(userCreateDto.getPassword());
        newUser.setRole(userCreateDto.getRole());
        return convertUserToDto(userRepository.save(newUser));
    }

    public UserDto createUserByAdmin(String token, UserCreateDto userCreateDto) {
        User user = getUserByToken(token);
        if (userCreateDto.getRole() == Role.STUDENT) {
            throw new RuntimeException("Use api/students to create student");
        }
        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Permission denied");
        }
        return creteUser(userCreateDto);
    }

    public TokenDto singIn(SingInDto singInDto) {
        User user = userRepository.findByLogin(singInDto.getLogin());
        if (user == null || !Objects.equals(user.getPassword(), singInDto.getPassword())) {
            throw new RuntimeException("Incorrect credentials");
        }

        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(createToken(singInDto));
        return tokenDto;
    }

    public String createToken(SingInDto singInDto) {
        byte[] token = (singInDto.getLogin() + ":" + singInDto.getPassword()).getBytes();
        return Base64.getEncoder().encodeToString(token);
    }

    public User getUserByToken(String token) {
        String encodedToken = new String(Base64.getDecoder().decode(token));
        String[] data = encodedToken.split(":", 2);

        if (data.length != 2) {
            throw new RuntimeException("Invalid token");
        }

        User user = userRepository.findByLogin(data[0]);
        if (user == null || !Objects.equals(user.getPassword(), data[1])) {
            throw new RuntimeException("Invalid token");
        }
        return user;
    }

    public boolean isAdnminOrMentor(String token) {
        User user = getUserByToken(token);
        return user.getRole() != Role.STUDENT;
    }

    public String generatePassword() {
        StringBuilder password = new StringBuilder();
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&*?";
        int length = 20;

        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(chars.length());
            password.append(chars.charAt(randomIndex));
        }

        return password.toString();
    }



    public UserDto convertUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        return userDto;
    }
}
