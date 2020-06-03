package com.springboot.udacity.assembler;

import com.springboot.udacity.domain.Person;
import com.springboot.udacity.dto.PersonDTO;
import org.springframework.stereotype.Component;

@Component
public class PersonAssembler {
    public Person toPerson(String firstName, String lastName) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return person;
    }

    public PersonDTO toPersonDTO(Person person) {
        PersonDTO personDTO = new PersonDTO(person.getId(), person.getFirstName(), person.getLastName());
        return personDTO;
    }
}
