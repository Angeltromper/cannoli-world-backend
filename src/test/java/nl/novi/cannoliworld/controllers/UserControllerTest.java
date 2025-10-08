package nl.novi.cannoliworld.controllers;
import nl.novi.cannoliworld.dtos.UserDto;
import nl.novi.cannoliworld.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    @DisplayName("Gebruiker aanmaken als de gebruiker niet bestaat")
    void createUserWhenUserDoesNotExist() {


        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        UserDto dto = new UserDto();
        dto.setUsername("Angeltr");
        dto.setPassword("Angeltr123");
        dto.setEmailAddress("test@test.nl");

        ResponseEntity<Void> response = userController.createUser(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }
}


