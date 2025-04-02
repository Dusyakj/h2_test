package com.example.demo.service;

import com.example.demo.dto.SignInDto;
import com.example.demo.dto.TokenDto;
import com.example.demo.dto.UserCreateDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.enums.Role;
import com.example.demo.exception.BadRequestRuntimeException;
import com.example.demo.exception.NotFoundRuntimeException;
import com.example.demo.exception.PermissionDeniedRuntimeException;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto creteUser(UserCreateDto userCreateDto) {
        User user = userRepository.findByLogin(userCreateDto.getLogin());
        if (user != null) {
            throw new BadRequestRuntimeException("User already exists");
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
            throw new BadRequestRuntimeException("Use api/students to create student");
        }
        if (user.getRole() != Role.ADMIN) {
            throw new PermissionDeniedRuntimeException("Permission denied");
        }
        return creteUser(userCreateDto);
    }

    public TokenDto signIn(SignInDto signInDto) {
        User user = userRepository.findByLogin(signInDto.getLogin());
        if (user == null || !Objects.equals(user.getPassword(), signInDto.getPassword())) {
            throw new BadRequestRuntimeException("Incorrect credentials");
        }

        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(createToken(signInDto));
        return tokenDto;
    }

    public String createToken(SignInDto signInDto) {
        byte[] token = (signInDto.getLogin() + ":" + signInDto.getPassword()).getBytes();
        return Base64.getEncoder().encodeToString(token);
    }

    public User getUserByToken(String token) {
        String[] data = token.split(" ", 2);
        if (data.length != 2 || !Objects.equals(data[0], "Basic")) {
            throw new BadRequestRuntimeException("Invalid token");
        }
        String encodedToken = new String(Base64.getDecoder().decode(data[1]));
        String[] credentials = encodedToken.split(":", 2);

        if (credentials.length != 2) {
            throw new BadRequestRuntimeException("Invalid token");
        }

        User user = userRepository.findByLogin(credentials[0]);
        if (user == null || !Objects.equals(user.getPassword(), credentials[1])) {
            throw new BadRequestRuntimeException("Invalid token");
        }
        return user;
    }

    public void checkPermission(String token) {
        User user = getUserByToken(token);
        if (user.getRole() == Role.STUDENT) {
            throw new PermissionDeniedRuntimeException("Permission denied");
        }
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

    public void deleteUserByLogin(String login) {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new NotFoundRuntimeException("User not found");
        }
        userRepository.delete(user);
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
