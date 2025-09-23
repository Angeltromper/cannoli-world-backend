package nl.novi.cannoliworld.controllers;

import nl.novi.cannoliworld.exeptions.BadRequestException;
import nl.novi.cannoliworld.models.User;
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

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        User user = new User();
        user.setUsername("Angeltr");
        user.setPassword("Angeltr123");
        user.setEmail("test@test.nl");

        ResponseEntity<Void> response = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }
}


