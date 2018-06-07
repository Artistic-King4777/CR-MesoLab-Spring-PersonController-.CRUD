package io.zipcoder.crudapp.controller;

import io.zipcoder.crudapp.CRUDApplication;
import io.zipcoder.crudapp.domain.Person;
import io.zipcoder.crudapp.service.PersonService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    private org.slf4j.Logger log = LoggerFactory.getLogger(SpringApplication.class);

    @Autowired
    private PersonService personService;

    /// CHANGE RETURN TYPE TO A PERSON FOR getPerson // createPerson // updatePerson
    // AND ADD RESPONSE ENTITY to all of them and HTTPStatus


    // is GET by default if theres no request method
    @RequestMapping("/people")
    public ResponseEntity<?> getPersonLsit() {
        List p = personService.getPersonList();

        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    // need requestBody  --  a body to put person in
    @RequestMapping(value="/people", method = RequestMethod.POST)
    public ResponseEntity<?> createPerson(@RequestBody Person person) {
        personService.createPerson(person);

        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }


    @RequestMapping(value="/people/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPerson(@PathVariable Long id) {
        Person person = personService.getPerson(id);

        if (person == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(person, HttpStatus.OK);
        }

    }

    @RequestMapping(value="/people/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePerson(@RequestBody Person person, @PathVariable Long id) {

        //Wont be null since im saving someone (i dont knw how to change it)
        log.info(String.valueOf("PERSON: "));

        if (personService.getPerson(id) == personService.updatePerson(person)) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @RequestMapping(value="/people/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);

        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }



}
