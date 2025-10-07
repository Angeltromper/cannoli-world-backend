package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailServiceTest {

    @Mock
    UserService userService;

    @InjectMocks
    CustomUserDetailService customUserDetailService;

    @Test
    @DisplayName("loadUserByUsername — user niet gevonden → UsernameNotFoundException")
    void loadUserByUsername_userNotFound_throws() {
        when(userService.getUser("unknown")).thenReturn(Optional.empty());

        UsernameNotFoundException ex = assertThrows(
                UsernameNotFoundException.class,
                () -> customUserDetailService.loadUserByUsername("unknown")
        );
        assertTrue(ex.getMessage().contains("User unknown not found"));

        verify(userService).getUser("unknown");
        verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("loadUserByUsername — user gevonden → geeft UserDetails van service terug")
    void loadUserByUsername_userFound_returnsSameDetails() {
        User domainUser = new User();
        domainUser.setUsername("test");
        domainUser.setPassword("pw");

        when(userService.getUser("test")).thenReturn(Optional.of(domainUser));

        UserDetails details = customUserDetailService.loadUserByUsername("test");

        assertSame(domainUser, details);
        assertEquals("test", details.getUsername());

        verify(userService).getUser("test");
        verifyNoMoreInteractions(userService);
    }
}
