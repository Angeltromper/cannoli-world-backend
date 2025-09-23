package nl.novi.cannoliworld.controllers;

import javax.validation.Valid;
import nl.novi.cannoliworld.dtos.PersonDto;
import nl.novi.cannoliworld.dtos.PersonInputDto;
import nl.novi.cannoliworld.models.Person;
import nl.novi.cannoliworld.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<PersonDto> getPersonList(
            @RequestParam(value = "firstname", required = false, defaultValue = "") String personFirstname,
            @RequestParam(value = "lastname",  required = false, defaultValue = "") String personLastname
    ) {
        List<Person> persons;
        boolean hasFirst = personFirstname != null && !personFirstname.isBlank();
        boolean hasLast  = personLastname  != null && !personLastname.isBlank();

        if (!hasFirst && !hasLast) {
            persons = personService.getPersonList();
        } else if (hasFirst && !hasLast) {
            persons = personService.findPersonListByPersonFirstname(personFirstname);
        } else {
            persons = personService.findPersonListByPersonLastname(personLastname);
        }

        var dtos = new ArrayList<PersonDto>(persons.size());
        for (Person p : persons) dtos.add(PersonDto.fromPerson(p));
        return dtos;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @personSecurity.isOwnerByPersonId(#id, authentication)")
    public PersonDto getPerson(@PathVariable Long id) {
        var person = personService.getPerson(id);
        return PersonDto.fromPerson(person);
    }

    @GetMapping("/by-username/{username}")
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.name")
    public PersonDto getByUsername(@PathVariable String username) {
        var person = personService.getByUsername(username);
        return PersonDto.fromPerson(person);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public PersonDto me(Authentication auth) {
        var person = personService.getByUsername(auth.getName());
        return PersonDto.fromPerson(person);
    }

    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public PersonDto updateMe(@Valid @RequestBody PersonInputDto dto, Authentication auth) {
        var current = personService.getByUsername(auth.getName());
        var updated = personService.updatePerson(current.getId(), dto.toPerson());
        return PersonDto.fromPerson(updated);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PersonDto> savePerson(@Valid @RequestBody PersonInputDto dto) {
        var saved = personService.savePerson(dto.toPerson());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(PersonDto.fromPerson(saved));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @personSecurity.isOwnerByPersonId(#id, authentication)")
    public PersonDto updatePerson(@PathVariable Long id, @Valid @RequestBody PersonInputDto dto) {
        var updated = personService.updatePerson(id, dto.toPerson());
        return PersonDto.fromPerson(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
