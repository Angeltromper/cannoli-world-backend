package nl.novi.cannoliworld.models;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    @DisplayName("Setters vullen velden; getters geven dezelfde waarden terug")
    void settersAndGettersWork() {
        var person = new Person();
        var image  = new FileUploadResponse(); // zorg dat no-args ctor bestaat

        user.setEnabled(true);
        user.setApikey("123");
        user.setEmail("test@test.com");
        user.setPerson(person);
        user.setId(1L);
        user.setImage(image);

        assertAll(
                () -> assertTrue(user.isEnabled()),
                () -> assertEquals("123", user.getApikey()),
                () -> assertEquals("test@test.com", user.getEmail()),
                () -> assertEquals(person, user.getPerson()),
                () -> assertEquals(1L, user.getId()),
                () -> assertEquals(image, user.getImage())
        );
    }

    @Test
    @DisplayName("Authorities: add en remove werken en set bevat verwachte rol(len)")
    void authoritiesAddAndRemove() {
        var a = new Authority("username", "ROLE_USER");

        user.addAuthority(a);
        assertTrue(user.getRoles().contains(a));
        assertEquals(Set.of(a), user.getRoles());

        user.removeAuthority(a);
        assertFalse(user.getRoles().contains(a));
        assertTrue(user.getRoles().isEmpty());
    }
}