package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.exceptions.RecordNotFoundException;
import nl.novi.cannoliworld.models.Person;
import nl.novi.cannoliworld.repositories.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    private static Person person(Long id, String first, String last) {
        Person p = new Person();
        p.setId(id);
        p.setPersonFirstname(first);
        p.setPersonLastname(last);
        return p;
    }

    @Test
    @DisplayName("savePerson - slaat op wanneer de persoon geldig is")
    void savePersonWhenPersonIsNotTaken() {
        Person person = person(null, "test", "test");
        person.setPersonStreetName("test");
        person.setPersonHouseNumber("test");
        person.setPersonHouseNumberAdd("test");
        person.setPersonCity("test");
        person.setPersonZipcode("test");

        when(personRepository.save(person)).thenReturn(person);

        Person savedPerson = personService.savePerson(person);

        assertEquals(person, savedPerson);
        verify(personRepository).save(person);
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    @DisplayName("updatePerson - werkt wanneer persoon bestaat")
    void updatePersonWhenPersonExists() {
        Person person1 = person(1L, "test", "test");
        person1.setPersonStreetName("test");
        person1.setPersonHouseNumber("test");
        person1.setPersonHouseNumberAdd("test");
        person1.setPersonCity("test");
        person1.setPersonZipcode("test");

        when(personRepository.findById(1L)).thenReturn(java.util.Optional.of(person1));
        when(personRepository.save(person1)).thenReturn(person1);

        person1.setPersonFirstname("Angel");
        personService.updatePerson(1L, person1);

        verify(personRepository).findById(1L);
        verify(personRepository).save(person1);
        verifyNoMoreInteractions(personRepository);

        assertThat(person1.getId()).isEqualTo(1);
        assertThat(person1.getPersonFirstname()).isEqualTo("Angel");
    }

    @Test
    @DisplayName("updatePerson - gooit RecordNotFoundException wanneer persoon niet bestaat")
    void updatePersonWhenPersonDoesNotExistThenThrowException() {
        Long id = 1L;
        Person person = new Person();

        when(personRepository.findById(id)).thenReturn(java.util.Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> personService.updatePerson(id, person));

        verify(personRepository).findById(id);
        verify(personRepository, never()).save(any());
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    @DisplayName("deletePerson - verwijdert wanneer persoon bestaat")
    void deletePersonWhenPersonExists() {
        when(personRepository.existsById(1L)).thenReturn(true);

        personService.deletePerson(1L);

        verify(personRepository).existsById(1L);
        verify(personRepository).deleteById(1L);
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    @DisplayName("deletePerson - gooit IllegalStateException wanneer persoon niet bestaat")
    void deletePersonWhenPersonDoesNotExistThenThrowsException() {
        Long id = 1L;
        when(personRepository.existsById(id)).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> personService.deletePerson(id));

        verify(personRepository).existsById(id);
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    @DisplayName("findPersonListByPersonFirstname - geeft lijst terug bij matches")
    void findPersonListByPersonFirstnameWhenPersonFirstnameIsFoundThenReturnTheListOfPersons() {
        List<Person> personList = Arrays.asList(
                person(1L, "Mieke", "Bloemendaal"),
                person(2L, "Mieke", "Bloemendaal")
        );

        when(personRepository.findByPersonFirstnameContainingIgnoreCase("Mieke"))
                .thenReturn(personList);

        List<Person> result = personService.findPersonListByPersonFirstname("Mieke");

        assertEquals(2, result.size());
        verify(personRepository).findByPersonFirstnameContainingIgnoreCase("Mieke");
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    @DisplayName("findPersonListByPersonFirstname - gooit RecordNotFoundException bij geen matches")
    void findPersonListByPersonFirstnameWhenPersonFirstnameIsNotFoundThenThrowException() {
        String personFirstname = "test";
        when(personRepository.findByPersonFirstnameContainingIgnoreCase(personFirstname))
                .thenReturn(Arrays.asList());

        assertThrows(
                RecordNotFoundException.class,
                () -> personService.findPersonListByPersonFirstname(personFirstname));

        verify(personRepository).findByPersonFirstnameContainingIgnoreCase(personFirstname);
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    @DisplayName("getPersonList - retourneert alle personen")
    void getPersonListShouldReturnsAllPersons() {
        List<Person> personList = Arrays.asList(new Person(), new Person());
        when(personRepository.findAll()).thenReturn(personList);

        List<Person> result = personService.getPersonList();

        assertEquals(2, result.size());
        verify(personRepository).findAll();
        verifyNoMoreInteractions(personRepository);
    }
}
