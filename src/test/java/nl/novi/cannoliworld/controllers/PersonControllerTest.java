package nl.novi.cannoliworld.controllers;

import nl.novi.cannoliworld.dtos.PersonDto;
import nl.novi.cannoliworld.models.Person;
import nl.novi.cannoliworld.repositories.PersonRepository;
import nl.novi.cannoliworld.service.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Mock
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonController personController;

    @Test
    @DisplayName("Should delete the person when the id is valid")
    void deletePersonWhenIdIsValid() {
        Person person = new Person();
        person.setId(1L);
        person.setPersonFirstname("Maria");
        person.setPersonLastname("Kopers");
        person.setPersonStreetName("Straat");
        person.setPersonHouseNumber("1");
        person.setPersonHouseNumberAdd("a");
        person.setPersonCity("Amsterdam");
        person.setPersonZipcode("1248RL");

        personService.deletePerson(1L);

        verify(personService, times(1)).deletePerson(1L);
    }

    @Test
    @DisplayName("Should save the person when the id is valid")
    void savePersonWhenIdIsValid() {
        Person person = new Person();
        person.setPersonFirstname("Test");
        person.setPersonLastname("Test");
        person.setPersonStreetName("Test");
        person.setPersonHouseNumber("Test");
        person.setPersonHouseNumberAdd("Test");
        person.setPersonCity("Test");
        person.setPersonZipcode("Test");

        personRepository.save(person);

        assertThat(person.getPersonFirstname()).isEqualTo("Test");

    }

    @Test
    @DisplayName("Should returns the person when the id is valid")
    void getPersonWhenIdIsValid() {
        Person person = new Person();
        person.setId(1L);
        person.setPersonFirstname("Angelique");
        person.setPersonLastname("Tromper");
        person.setPersonStreetName("Spaandammerdijk");
        person.setPersonHouseNumber("1");
        person.setPersonHouseNumberAdd("a");
        person.setPersonCity("Amsterdam");
        person.setPersonZipcode("1014AA");

        when(personService.getPerson( 1L)).thenReturn(person);

        PersonDto result = personController.getPerson(1L);

        assertNotNull(result);
        assertEquals(1L, result.id);
        assertEquals("Angelique", result.personFirstname);
        assertEquals("Tromper", result.personLastname);
        assertEquals("Spaandammerdijk", result.personStreetName);
        assertEquals("1", result.personHouseNumber);
        assertEquals("a", result.personHouseNumberAdd);
        assertEquals("Amsterdam", result.personCity);
        assertEquals("1014AA", result.personZipcode);
    }

    @Test
    @DisplayName("Should return all person when no parameters are given")
    void getPersonListWhenNoParaMetersAreGivenThenReturnAllPersons() {
      var person1 = new Person();
      person1.setId(1L);
      person1.setPersonFirstname("Maria");
      person1.setPersonLastname("Kopers");

        var person2 = new Person();
        person2.setId(2L);
        person2.setPersonFirstname("Loes");
        person2.setPersonLastname("Bloemendaal");

        var person3 = new Person();
        person3.setId(3L);
        person3.setPersonFirstname("Frans");
        person3.setPersonLastname("Spaans");

        var persons = List.of(person1, person2, person3);

        when(personService.getPersonList()).thenReturn(persons);

        var result = personController.getPersonList(null,null);

        assertEquals(3, result.size());
    }
}


