package com.example.demo.controller;

import com.example.demo.dto.PersonDto;
import com.example.demo.dto.ProblemDto;
import com.example.demo.entity.Person;
import com.example.demo.entity.Problem;
import com.example.demo.service.PersonService;
import com.example.demo.service.ProblemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api")
public class TestController {

    private PersonService personService;
    private ProblemService problemService;

    public TestController(PersonService personService, ProblemService problemService) {
        this.personService = personService;
        this.problemService = problemService;
    }

    @PostMapping("/person")
    public Person addPerson(@RequestBody PersonDto person) {
        return personService.createPerson(person);
    }

    @PostMapping("/problem")
    public Problem addPerson(@RequestBody ProblemDto problem) {
        return problemService.createProblem(problem);
    }

    @PostMapping("/{id1}/{id2}")
    public void addPerson(@PathVariable Long id1, @PathVariable Long id2 ) {
        personService.addProblemToPerson(id1, id2);
    }

    @GetMapping("/person/{id}")
    public Optional<Person> getPerson(@PathVariable long id ){
        return personService.getPerson(id);
    }

    @DeleteMapping("/problem/{id}")
    public void deleteProblem(@PathVariable long id) {
        problemService.deleteProblem(id);
    }

}
