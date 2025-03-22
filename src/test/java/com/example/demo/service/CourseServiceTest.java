package com.example.demo.service;

import com.example.demo.dto.CourseCreateDto;
import com.example.demo.dto.CourseDto;
import com.example.demo.entity.Course;
import com.example.demo.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCourse() {
        CourseCreateDto courseCreateDto = new CourseCreateDto();
        courseCreateDto.setTitle("Test Course");
        courseCreateDto.setDescription("Test Description");

        Course course = new Course();
        course.setId(1L);
        course.setTitle("Test Course");
        course.setDescription("Test Description");

        when(courseRepository.save(any(Course.class))).thenReturn(course);

        CourseDto result = courseService.createCourse(courseCreateDto);

        assertEquals(course.getId(), result.getId());
        assertEquals(course.getTitle(), result.getTitle());
        assertEquals(course.getDescription(), result.getDescription());
    }

    @Test
    void testGetCourse() {
        Course course = new Course();
        course.setId(1L);
        course.setTitle("Test Course");
        course.setDescription("Test Description");

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        CourseDto result = courseService.getCourse(1L);

        assertEquals(course.getId(), result.getId());
        assertEquals(course.getTitle(), result.getTitle());
        assertEquals(course.getDescription(), result.getDescription());
    }

    @Test
    void testDeleteCourse() {
        Course course = new Course();
        course.setId(1L);
        course.setTitle("Test Course");
        course.setDescription("Test Description");

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        courseService.deleteCourse(1L);

        verify(courseRepository, times(1)).delete(course);
    }
}