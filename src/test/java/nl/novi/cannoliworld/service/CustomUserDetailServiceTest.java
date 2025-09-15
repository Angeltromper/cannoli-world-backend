package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.models.Authority;
import nl.novi.cannoliworld.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomUserDetailService customUserDetailService;

    @Test
    @DisplayName("loadUserByUsername — user niet gevonden → UsernameNotFoundException")
    void loadUserByUsername_userNotFound_throws() {
        when(userService.getUser("unknown")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailService.loadUserByUsername("unknown"));

        verify(userService).getUser("unknown");
        verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("loadUserByUsername — user gevonden → UserDetails met rollen")
    void loadUserByUsername_userFound_returnsDetails() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.addAuthority(new Authority("test", "ROLE_USER"));
        user.addAuthority(new Authority("test", "ROLE_ADMIN"));

        when(userService.getUser("test")).thenReturn(Optional.of(user));

        UserDetails details = customUserDetailService.loadUserByUsername("test");

        assertEquals("test", details.getUsername());
        assertEquals("test", details.getPassword());
        assertEquals(2, details.getAuthorities().size());
        assertTrue(details.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(details.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));

        verify(userService).getUser("test");
        verifyNoMoreInteractions(userService);
    }
}
