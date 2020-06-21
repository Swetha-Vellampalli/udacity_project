package com.springboot.udacity.controller;

import com.springboot.udacity.mapper.PersonMapper;
import com.springboot.udacity.domain.Person;
import com.springboot.udacity.dto.PersonDTO;
import com.springboot.udacity.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    @RequestMapping("/person")
    public PersonDTO post(@RequestParam(value = "firstName", defaultValue = "testFirstName") String firstName,
                          @RequestParam(value = "lastName", defaultValue = "testLastName") String lastName) {

        Person person = personMapper.toPerson(firstName, lastName);
        personRepository.save(person);
        PersonDTO personDTO = personMapper.toPersonDTO(person);

        return personDTO;
    }

    @GetMapping("/all")
    public List<Person> getAllPersons() {

        List<Person> person = new ArrayList<Person>();
        personRepository.findAll().forEach(person::add);

        return person;
    }

}