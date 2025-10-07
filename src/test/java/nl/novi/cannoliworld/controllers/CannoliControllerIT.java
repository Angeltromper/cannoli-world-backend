package nl.novi.cannoliworld.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.cannoliworld.config.PasswordEncoderBean;
import nl.novi.cannoliworld.dtos.CannoliInputDto;
import nl.novi.cannoliworld.filter.JwtRequestFilter;
import nl.novi.cannoliworld.models.Cannoli;
import nl.novi.cannoliworld.service.CannoliService;
import nl.novi.cannoliworld.service.CustomUserDetailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CannoliController.class)
@AutoConfigureMockMvc(addFilters = false)
class CannoliControllerIT {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CannoliService cannoliService;

    @MockBean
    CustomUserDetailService customUserDetailService;

    @MockBean
    JwtRequestFilter jwtRequestFilter;

    @MockBean
    PasswordEncoderBean passwordEncoderBean;

    @Test
    @DisplayName("GET /cannolis zonder params → 200 met lijst")
    void getCannolis_noParams_returnsList() throws Exception {
        Cannoli c = new Cannoli();
        c.setId(1L);
        c.setCannoliName("Test");
        c.setCannoliType("Type");
        c.setDescription("Desc");
        c.setIngredients("Ing");
        c.setPrice(1.0);

        when(cannoliService.getCannolis()).thenReturn(List.of(c));

        mvc.perform(get("/cannolis"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].cannoliName").value("Test"))
                .andExpect(jsonPath("$[0].price").value(1.0));
    }

    @Test
    @DisplayName("POST /cannolis met geldige invoer → 201 met body")
    void createCannoli_valid_returns201() throws Exception {
        CannoliInputDto in = new CannoliInputDto();
        in.setCannoliName("test");
        in.setCannoliType("type");
        in.setDescription("desc");
        in.setIngredients("ing");
        in.setPrice(1f);

        Cannoli saved = new Cannoli();
        saved.setId(10L);
        saved.setCannoliName("test");
        saved.setCannoliType("type");
        saved.setDescription("desc");
        saved.setIngredients("ing");
        saved.setPrice(1f);

        when(cannoliService.createCannoli(any())).thenReturn(saved);

        mvc.perform(post("/cannolis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.cannoliName").value("test"))
                .andExpect(jsonPath("$.price").value(1.0));
    }
}
