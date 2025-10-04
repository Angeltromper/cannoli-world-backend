package nl.novi.cannoliworld.controllers;
import nl.novi.cannoliworld.dtos.PersonDto;
import nl.novi.cannoliworld.dtos.PersonInputDto;
import nl.novi.cannoliworld.models.Person;
import nl.novi.cannoliworld.service.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Mock private PersonService personService;
    @InjectMocks private PersonController personController;

    private static Person person(Long id, String first, String last) {
        Person p = new Person();
        p.setId(id);
        p.setPersonFirstname(first);
        p.setPersonLastname(last);
        p.setPersonStreetName("Straat");
        p.setPersonHouseNumber("1");
        p.setPersonHouseNumberAdd("a");
        p.setPersonCity("Amsterdam");
        p.setPersonZipcode("1011AA");
        return p;
    }

    private static PersonInputDto input(String first, String last) {
        PersonInputDto in = new PersonInputDto();
        in.personFirstname = first;
        in.personLastname  = last;
        in.personStreetName = "Straat";
        in.personHouseNumber = "10";
        in.personHouseNumberAdd = "b";
        in.personCity = "Amsterdam";
        in.personZipcode = "1000AA";
        return in;
    }

    @Test
    @DisplayName("GET persons — all (no filters)")
    void getPersonList_returnsAll() {
        when(personService.getPersonList()).thenReturn(List.of(
                person(1L, "Maria", "Kopers"),
                person(2L, "Loes", "Bloemendaal")
        ));

        List<PersonDto> result = personController.getPersonList(null, null);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getPersonFirstname()).isEqualTo("Maria");

        verify(personService).getPersonList();
        verifyNoMoreInteractions(personService);
    }

    @Test
    @DisplayName("GET person/{id} — PersonDto mapping")
    void getPerson_returnsDto() {
        when(personService.getPerson(1L)).thenReturn(person(1L, "Angelique", "Tromper"));

        PersonDto dto = personController.getPerson(1L);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getPersonFirstname()).isEqualTo("Angelique");
        assertThat(dto.getPersonLastname()).isEqualTo("Tromper");

        verify(personService).getPerson(1L);
        verifyNoMoreInteractions(personService);
    }

    @Test
    @DisplayName("POST persons — input DTO → save → ResponseEntity<PersonDto> met Location")
    void savePerson_returnsDto() {
        MockHttpServletRequest req = new MockHttpServletRequest("POST", "/persons");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));

        var in = input("Test", "User");
        when(personService.savePerson(any(Person.class)))
                .thenReturn(person(10L, "Test", "User"));

        ResponseEntity<PersonDto> resp = personController.savePerson(in);
        PersonDto dto = resp.getBody();

        assertThat(resp.getStatusCodeValue()).isEqualTo(201);
        assertThat(resp.getHeaders().getLocation()).isNotNull();
        assertThat(resp.getHeaders().getLocation().toString()).endsWith("/persons/10");
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(10L);
        assertThat(dto.getPersonFirstname()).isEqualTo("Test");

        verify(personService).savePerson(any(Person.class));
        verifyNoMoreInteractions(personService);

        RequestContextHolder.resetRequestAttributes();

    }

    @Test
    @DisplayName("PUT person/{id} — update returns PersonDto")
    void updatePerson_returnsDto() {
        var in = input("Nieuw", "Naam");           // controller verwacht DTO
        var updatedEntity = person(5L, "Nieuw", "Naam");

        when(personService.updatePerson(eq(5L), any(Person.class)))
                .thenReturn(updatedEntity);

        PersonDto dto = personController.updatePerson(5L, in);

        assertThat(dto.getId()).isEqualTo(5L);
        assertThat(dto.getPersonFirstname()).isEqualTo("Nieuw");
        assertThat(dto.getPersonLastname()).isEqualTo("Naam");

        verify(personService).updatePerson(eq(5L), any(Person.class));
        verifyNoMoreInteractions(personService);
    }

    @Test
    @DisplayName("DELETE person/{id} — service called")
    void deletePerson_callsService() {
        personController.deletePerson(7L);

        verify(personService).deletePerson(7L);
        verifyNoMoreInteractions(personService);
    }
}
