package com.example.demo.controller;

import com.example.demo.dto.ProblemDto;
import com.example.demo.service.ProblemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ProblemControllerTest {

    @Mock
    private ProblemService problemService;

    @InjectMocks
    private ProblemController problemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProblem() {
        ProblemDto problemDto = new ProblemDto();
        problemDto.setId(1L);
        problemDto.setTitle("Test Problem");
        problemDto.setDescription("Test Description");

        when(problemService.getProblem(1L)).thenReturn(problemDto);

        ResponseEntity<ProblemDto> response = problemController.getProblem(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(problemDto, response.getBody());
    }

    @Test
    void testDeleteProblem() {
        ResponseEntity<Void> response = problemController.deleteProblem(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}