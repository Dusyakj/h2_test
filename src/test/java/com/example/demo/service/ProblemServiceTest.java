package com.example.demo.service;

import com.example.demo.dto.ProblemDto;
import com.example.demo.entity.Problem;
import com.example.demo.repository.ProblemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProblemServiceTest {

    @Mock
    private ProblemRepository problemRepository;

    @InjectMocks
    private ProblemService problemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProblem() {
        Problem problem = new Problem();
        problem.setId(1L);
        problem.setTitle("Test Problem");
        problem.setDescription("Test Description");

        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));

        ProblemDto result = problemService.getProblem(1L);

        assertEquals(problem.getId(), result.getId());
        assertEquals(problem.getTitle(), result.getTitle());
        assertEquals(problem.getDescription(), result.getDescription());
    }

    @Test
    void testDeleteProblem() {
        Problem problem = new Problem();
        problem.setId(1L);
        problem.setTitle("Test Problem");
        problem.setDescription("Test Description");

        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));

        problemService.deleteProblem(1L);

        verify(problemRepository, times(1)).delete(problem);
    }
}