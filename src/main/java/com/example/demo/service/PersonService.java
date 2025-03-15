package com.example.demo.service;

import com.example.demo.dto.PersonDto;
import com.example.demo.entity.Person;
import com.example.demo.entity.Problem;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ProblemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final ProblemRepository problemRepository;

    public PersonService(PersonRepository personRepository, ProblemRepository problemRepository) {
        this.personRepository = personRepository;
        this.problemRepository = problemRepository;
    }

    public Person createPerson(PersonDto personDto) {
        Person person = new Person();
        person.setName(personDto.getName());
        return personRepository.save(person);
    }

    public Optional<Person> getPerson(long id) {
        return personRepository.findById(id);
    }

    @Transactional
    public void addProblemToPerson(Long personId, Long problemId) {
        Person person = personRepository.findById(personId).orElseThrow(() -> new RuntimeException("Person not found"));
        Problem problem = problemRepository.findById(problemId).orElseThrow(() -> new RuntimeException("Problem not found"));

        person.addProblem(problem);
        personRepository.save(person);
    }
}