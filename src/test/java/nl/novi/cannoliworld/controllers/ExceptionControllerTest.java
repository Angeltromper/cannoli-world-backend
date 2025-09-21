package nl.novi.cannoliworld.controllers;

import nl.novi.cannoliworld.exeptions.RecordNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ExceptionControllerTest {

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .standaloneSetup(new BoomController())
                .setControllerAdvice(new ExceptionController())
                .build();
    }

    @Test
    void recordNotFound_isMappedTo404_withMessageInBody() throws Exception {
        mvc.perform(get("/boom/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("Resource not found")));
    }

    @Test
    void validationError_isMappedTo400() throws Exception {
        String badJson = "{ \"name\": \"\" }";
        mvc.perform(post("/boom/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @RestController
    static class BoomController {
        @GetMapping("/boom/not-found")
        String notFound() { throw new RecordNotFoundException("Resource not found"); }

        @PostMapping("/boom/validate")
        String validate(@RequestBody @Valid NameDto dto) { return "ok"; }
    }

    static class NameDto {
        @NotBlank
        public String name;
    }
}


