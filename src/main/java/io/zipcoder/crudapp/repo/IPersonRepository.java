package io.zipcoder.crudapp.repo;

import io.zipcoder.crudapp.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface IPersonRepository extends CrudRepository<Person, Long> {
}
