package nl.novi.cannoliworld.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    UserTest() {
    }

    @BeforeEach
    void setUp() { user = new User(); }

    @Test
    @DisplayName("Should set the enabled to true")
    void setEnableWhenEnabledIsTrue() {
        user.setEnabled(true);
        assertTrue(user.IsEnabled());
    }

    @Test
    @DisplayName("Should set the enabled to false")
    void setEnabledWhenEnabledIsFalse() {
        user.setEnabled(false);
        assertFalse(user.IsEnabled());
    }

    @Test
    @DisplayName("Should set the apikey")
    void setApiKey() {
        user.setApikey("123");
        assertEquals("123",user.getApikey());
    }

    @Test
    @DisplayName("Should set the emailAdress")
    void setEmailAdress() {
        user.setEmail("test@test.com");
        assertEquals("test@test.com", user.getEmail());
    }

    @Test
    @DisplayName("Should return the emailAdress of the user")
    void getEmailAdressShouldReturnTheEmailAdressOfTheUser() {
        user.setEmail("test@test.com");
        assertEquals("test@test.com", user.getEmail());
    }

    @Test
    @DisplayName("Should return the person when the person exists")
    void getPersonWhenPersonExists() {
        Person person = new Person();
        user.setPerson(person);
        assertEquals(person, user.getPerson());
    }

    @Test
    @DisplayName("Should return null when the person does not exist")
    void getPersonWhenPersonDoesNotExistThenReturnNull() { assertNull(user.getPerson()); }

    @Test
    @DisplayName("Should set the person")
    void setPerson() {
        Person person = new Person();
        user.setPerson(person);
        assertEquals(person, user.getPerson());
    }

    @Test
    @DisplayName("Should return the authorities of the user")
    void getAuthoritiesShouldReturnAuthoritiesOfUser() {
        Authority authority = new Authority("username", "authority");
        user.addAuthority(authority);
        assertEquals(user.getRoles(), Set.of(authority));
    }

    @Test
    @DisplayName("Should add the authority to the authorities set")
    void addAuthorityShouldAddTheAuthorityToTheAuthoritiesSet() {
        Authority authority = new Authority("username", "authority");
        user.addAuthority(authority);
        assertTrue(user.getRoles().contains(authority));
    }



    @Test
    @DisplayName("Should remove the authority from the user")
    void removeAuthorityShouldRemovesTheAuthorityFromTheUser() {
        Authority authority = new Authority("username", "authority");
        user.addAuthority(authority);
        user.removeAuthority(authority);
        assertFalse(user.getRoles().contains(authority));
    }

    @Test
    @DisplayName("Should return the id of the user")
    void getIdShouldReturnTheIdOfTheUser() {
        Long id = 1L;
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    @DisplayName("Should set the id")
    void setId() {
        user.setId(1L);
        assertEquals(1L, user.getId());
    }

    @Test
    @DisplayName("Should return the image of the user")
    void getImageShouldReturnTheImageOfTheUser() {
        FileUploadResponse image = new FileUploadResponse();
        user.setImage(image);
        assertEquals(image, user.getImage());
    }

    @Test
    @DisplayName("Should set the image")
    void setImage() {
        FileUploadResponse image = new FileUploadResponse();
        user.setImage(image);
        assertEquals(image, user.getImage());
    }
}





