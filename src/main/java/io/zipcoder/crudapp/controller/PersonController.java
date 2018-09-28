package io.zipcoder.crudapp.controller;

import io.zipcoder.crudapp.CRUDApplication;
import io.zipcoder.crudapp.domain.Person;
import io.zipcoder.crudapp.service.PersonService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import io.zipcoder.crudapp.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    private org.slf4j.Logger log = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    /// CHANGE RETURN TYPE TO A PERSON FOR getPerson // createPerson // updatePerson
    // AND ADD RESPONSE ENTITY to all of them and HTTPStatus



    // protected is an ACCESS MODIFIER
    protected void verifyID(Long id) throws ResourceNotFoundException {

            if(personService.verifyPerson(id) == null) {
                throw new ResourceNotFoundException("Person with id: " +
                        id + ", is not found. Please try again");
            }




        // TODO -- try saying later if verifyPerson != getPerson { sout =
        // TODO --   throw a hand made method for asking for the wrong person
        // TODO --   and else sout Unknown response

    }




    // is GET by default if theres no request method
    @RequestMapping("/people")
    public ResponseEntity<?> getPersonList() {
        List p = personService.getPersonList();

        log.info("GET ALL:  " + p);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }



    // need requestBody  --  a body to put person in
    @RequestMapping(value="/people", method = RequestMethod.POST)
    public ResponseEntity<?> createPerson(@RequestBody Person person) {
        Person p = personService.createPerson(person);

        log.info("Person CREATED " + p);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }


    @RequestMapping(value="/people/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPerson(@PathVariable Long id) throws StackOverflowError {
        Person person = personService.getPerson(id);

        if (person == null) {
            log.info("Could Not Find a person with this id");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.info("'GOT' one: " + personService.getPerson(id));
            return new ResponseEntity<>(person, HttpStatus.OK);
        }

    }

    @RequestMapping(value="/people/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePerson(@RequestBody Person person, @PathVariable Long id) {
        log.info("Verifying");
        verifyID(id);

        if (personService.getPerson(id) == personService.updatePerson(person)) {
            log.info("Person UPDATED : " + getPerson(id));
            return new ResponseEntity<>(person, HttpStatus.OK);
        } else {
            log.info("Person CREATED: " + getPerson(id));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @RequestMapping(value="/people/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePerson(@PathVariable Long id) {
        log.info("Verifying");
        verifyID(id);

        personService.deletePerson(id);

        log.info("Person DELETED: " + getPerson(id));
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }




}
