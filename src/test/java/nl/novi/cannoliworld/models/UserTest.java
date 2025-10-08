package nl.novi.cannoliworld.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("getAuthorities geeft de rollen uit getRoles terug")
    void authoritiesExposeRoles() {
        User u = new User();
        u.setUsername("test");

        Authority roleUser = new Authority();
        roleUser.setUsername("test");
        roleUser.setAuthority("ROLE_USER");

        Authority roleAdmin = new Authority();
        roleAdmin.setUsername("test");
        roleAdmin.setAuthority("ROLE_ADMIN");

        u.setRoles(Set.of(roleUser, roleAdmin));

        var authorities = u.getAuthorities();

        assertEquals(2, authorities.size());
        assertTrue(authorities.stream().anyMatch(a -> "ROLE_USER".equals(a.getAuthority())));
        assertTrue(authorities.stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority())));
    }

    @Test
    @DisplayName("rollen toevoegen en verwijderen werkt en wordt weerspiegeld in getAuthorities")
    void authoritiesAddAndRemove() {
        User u = new User();
        u.setUsername("test");

        Authority roleAdmin = new Authority();
        roleAdmin.setUsername("test");
        roleAdmin.setAuthority("ROLE_ADMIN");

        u.getRoles().add(roleAdmin);

        assertTrue(u.getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority())));

        u.getRoles().removeIf(a -> "ROLE_ADMIN".equals(a.getAuthority()));

        assertFalse(u.getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority())));
    }
}
