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
    Person getByUsername(String username);
//    void updatePerson(Long id, Person person);
    Person updatePerson(Long id, Person changes);
    void deletePerson(Long id);
}
