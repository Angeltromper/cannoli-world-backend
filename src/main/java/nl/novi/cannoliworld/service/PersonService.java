package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.models.Person;

import java.util.Collection;
import java.util.List;

public interface PersonService {

    List<Person> getPersonList();
    List<Person> findPersonListByPersonFirstname(String personFirstname);
    List<Person> findPersonListByPersonLastname(String personLastname);

    Person getPerson(Long id);
    Person savePerson(Person person);

    void updatePerson(Long id, Person person);

    void deletePerson(Long personId);
}
