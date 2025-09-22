package nl.novi.cannoliworld.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.cannoliworld.dtos.CannoliInputDto;
import nl.novi.cannoliworld.models.Cannoli;
import nl.novi.cannoliworld.repositories.CannoliRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CannoliControllerIT {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired CannoliRepository cannoliRepository;

    Cannoli classic;
    Cannoli pistachio;

    @BeforeEach
    void setUp() {
        cannoliRepository.deleteAll();

        classic = new Cannoli();
        classic.setCannoliName("Classic Ricotta");
        classic.setCannoliType("Classic");
        classic.setDescription("Traditional shell with sweet ricotta");
        classic.setIngredients("ricotta, shell, sugar");
        classic.setPrice(2.50);
        classic = cannoliRepository.save(classic);

        pistachio = new Cannoli();
        pistachio.setCannoliName("Pistachio Dream");
        pistachio.setCannoliType("Pistachio");
        pistachio.setDescription("Green goodness");
        pistachio.setIngredients("pistachio, ricotta, shell");
        pistachio.setPrice(3.00);
        pistachio = cannoliRepository.save(pistachio);
    }

    @Test
    @DisplayName("GET /cannolis")
    void getAll() throws Exception {
        mvc.perform(get("/cannolis"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[*].cannoliName", containsInAnyOrder("Classic Ricotta", "Pistachio Dream")))
                .andExpect(jsonPath("$[?(@.cannoliName=='Classic Ricotta')].price", notNullValue()));
    }

    @Test
    @DisplayName("GET /cannolis?cannoliName=Pista")
    void filterByName() throws Exception {
        mvc.perform(get("/cannolis").param("cannoliName", "Pista"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].cannoliName", is("Pistachio Dream")));
    }

    @Test
    @DisplayName("GET /cannolis?cannoliType=Classic")
    void filterByType() throws Exception {
        mvc.perform(get("/cannolis").param("cannoliType", "Classic"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].cannoliType", is("Classic")));
    }

    @Test
    @DisplayName("GET /cannolis/{id}")
    void getById() throws Exception {
        mvc.perform(get("/cannolis/{id}", classic.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(classic.getId().intValue())))
                .andExpect(jsonPath("$.cannoliName", is("Classic Ricotta")));
    }

    @Test
    @DisplayName("POST /cannolis valid")
    void create_valid() throws Exception {
        var dto = new CannoliInputDto();
        dto.cannoliName = "Chocolate Crunch";
        dto.cannoliType = "Chocolate";
        dto.description = "Cocoa shell with chips";
        dto.ingredients = "cocoa, shell, ricotta";
        dto.price = 2.75f;

        mvc.perform(post("/cannolis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.matchesPattern("/cannolis/\\d+")))
                .andExpect(jsonPath("$.cannoliName", is("Chocolate Crunch")))
                .andExpect(jsonPath("$.price", is(closeTo(2.75, 0.0001))));
    }

    @Test
    @DisplayName("POST /cannolis invalid")
    void create_invalid() throws Exception {
        var dto = new CannoliInputDto();
        dto.cannoliName = "";
        dto.cannoliType = "";
        dto.description = "";
        dto.ingredients = "";
        dto.price = 0f;

        mvc.perform(post("/cannolis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /cannolis/{id}")
    void update() throws Exception {
        var updated = new Cannoli();
        updated.setCannoliName("Classic Ricotta (Updated)");
        updated.setCannoliType("Classic");
        updated.setDescription("Even better");
        updated.setIngredients("ricotta, shell, sugar, love");
        updated.setPrice(2.60);

        mvc.perform(put("/cannolis/{id}", classic.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cannoliName", is("Classic Ricotta (Updated)")))
                .andExpect(jsonPath("$.price", is(closeTo(2.60, 0.0001))));
    }

    @Test
    @DisplayName("DELETE /cannolis/delete/{id}")
    void delete_thenNotFound() throws Exception {
        mvc.perform(delete("/cannolis/delete/{id}", pistachio.getId()))
                .andExpect(status().isNoContent());

        mvc.perform(get("/cannolis/{id}", pistachio.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /cannolis beide filters")
    void bothFilters() throws Exception {
        mvc.perform(get("/cannolis").param("cannoliName", "Classic").param("cannoliType", "Classic"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].cannoliType", everyItem(is("Classic"))));
    }
}
