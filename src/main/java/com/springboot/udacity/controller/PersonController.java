package com.springboot.udacity.controller;

import com.springboot.udacity.assembler.PersonAssembler;
import com.springboot.udacity.domain.Person;
import com.springboot.udacity.dto.PersonDTO;
import com.springboot.udacity.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonAssembler personAssembler;

    @RequestMapping("/person")
    public PersonDTO post(@RequestParam(value = "firstName", defaultValue = "defaultFirstName") String firstName, @RequestParam(value = "lastName", defaultValue = "defaultLastName") String lastName) {

        Person person = personAssembler.toPerson(firstName, lastName);
        personRepository.save(person);
        PersonDTO personDTO = personAssembler.toPersonDTO(person);

        return personDTO;
    }
}
