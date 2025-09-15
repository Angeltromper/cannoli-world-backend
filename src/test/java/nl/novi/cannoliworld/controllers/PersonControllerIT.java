package nl.novi.cannoliworld.controllers;

import nl.novi.cannoliworld.models.Person;
import nl.novi.cannoliworld.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PersonControllerIT {

    @Autowired MockMvc mvc;
    @Autowired PersonRepository personRepository;

    private Long personId;

    @BeforeEach
    void setUp() {
        personRepository.deleteAll();

        Person p = new Person();
        p.setPersonFirstname("Test");
        p.setPersonLastname("User");
        p.setPersonStreetName("Straat");
        p.setPersonHouseNumber("1");
        p.setPersonHouseNumberAdd("a");
        p.setPersonCity("Amsterdam");
        p.setPersonZipcode("1011AA");

        personId = personRepository.save(p).getId();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getPerson_returns200() throws Exception {
        mvc.perform(get("/persons/{id}", personId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(personId));
    }
}
