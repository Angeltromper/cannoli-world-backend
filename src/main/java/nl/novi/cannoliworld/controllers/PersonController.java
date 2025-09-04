package nl.novi.cannoliworld.controllers;

import nl.novi.cannoliworld.dtos.PersonDto;
import nl.novi.cannoliworld.dtos.PersonInputDto;
import nl.novi.cannoliworld.models.Person;
import nl.novi.cannoliworld.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/users")
    @Transactional
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
        for (Person p : persons) {
            dtos.add(PersonDto.fromPerson(p));
        }
        return dtos;
    }

    @GetMapping("/{id}")
    public PersonDto getPerson(@PathVariable("id") Long id) {
        var person = personService.getPerson(id);
        return PersonDto.fromPerson(person);
    }

    // 🔹 NIEUW: ophalen op basis van username (gekoppelde User.username)
    @GetMapping("/by-username/{username}")
    public PersonDto getByUsername(@PathVariable String username) {
        var person = personService.getByUsername(username);
        return PersonDto.fromPerson(person);
    }

    @PostMapping
    public PersonDto savePerson(@RequestBody PersonInputDto dto) {
        var person = personService.savePerson(dto.toPerson());
        return PersonDto.fromPerson(person);
    }

    @PutMapping("/{id}")
    public PersonDto updatePerson(@PathVariable Long id, @RequestBody Person person) {
        personService.updatePerson(id, person);
        return PersonDto.fromPerson(person);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable("id") Long personId) {
        personService.deletePerson(personId);
    }
}
