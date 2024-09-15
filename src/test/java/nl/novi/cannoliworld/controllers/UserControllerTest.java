package nl.novi.cannoliworld.controllers;

import nl.novi.cannoliworld.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService4 userService;

    @BeforeEach
    public void setup() {
        User user = new User();
        user.setUsername("tester");
    }

    @Test
    public void getUsersReturnsStatusOk() throws Exception {
        mockMvc.perform(get("/users/"))
                .andExpect(status().isOk());
    }

    @Test
    void retrieveUser() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users/tester"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}



