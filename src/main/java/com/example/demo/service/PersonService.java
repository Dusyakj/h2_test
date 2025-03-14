package com.example.demo.service;

import com.example.demo.dto.PersonDto;
import com.example.demo.entity.Person;
import com.example.demo.entity.Problem;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ProblemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {
    private PersonRepository personRepository;
    
    private ProblemRepository problemRepository;

    public PersonService(PersonRepository personRepository, ProblemRepository problemRepository) {
        this.personRepository = personRepository;
        this.problemRepository = problemRepository;
    }

    public Person createPerson(PersonDto person) {
        Person person1 = new Person();
        person1.setName(person.getName());
        return personRepository.save(person1);
    }

    public Optional<Person> getPerson(long id) {
        return personRepository.findById(id);
    }

    public Person addProblemToPerson(Long personId, Long problemId) {
        Person person = personRepository.findById(personId).orElse(null);
        Problem problem = problemRepository.findById(problemId).orElse(null);

        if (person != null && problem != null) {
            person.addProblem(problem);
            personRepository.save(person);
        }

        return person;
    }
}
