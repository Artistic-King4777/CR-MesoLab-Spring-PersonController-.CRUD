package io.zipcoder.crudapp.service;

import io.zipcoder.crudapp.domain.Person;
import io.zipcoder.crudapp.repo.IPersonRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import io.zipcoder.crudapp.exception.*;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class PersonService {

    @Autowired
    private IPersonRepository personRepository;


    public Person verifyPerson(Long id) throws ResourceNotFoundException{
        return personRepository.findOne(id);
    }


    // WORK Yes/No
    public List<Person> getPersonList() {
        List<Person> people = new ArrayList<>();
        // need a lambda function
        // go into db find all people // loop throo each one and add the people

        personRepository.findAll().forEach(people::add);

        return people;
    }


    public Person createPerson(Person person) {
        return personRepository.save(person);
    }


    public Person getPerson(Long id) {
         return personRepository.findOne(id);
    }

    public Person updatePerson(Person person) {
        return personRepository.save(person);
    }

    public void deletePerson(Long id) {
        personRepository.delete(id);
    }





} // END OF CLASS
