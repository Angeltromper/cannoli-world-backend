package nl.novi.cannoliworld.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest {

    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person();
    }

    @Test
    @DisplayName("Setters vullen velden; getters geven dezelfde waarden terug")
    void settersAndGettersWork() {
        person.setId(1L);
        person.setPersonFirstname("Angel");
        person.setPersonLastname("Test");
        person.setPersonStreetName("TestStreet");
        person.setPersonHouseNumber("7");
        person.setPersonHouseNumberAdd("B");
        person.setPersonCity("Amsterdam");
        person.setPersonZipcode("1234AB");

        assertAll(
                () -> assertEquals(1L, person.getId()),
                () -> assertEquals("Angel", person.getPersonFirstname()),
                () -> assertEquals("Test", person.getPersonLastname()),
                () -> assertEquals("TestStreet", person.getPersonStreetName()),
                () -> assertEquals("7", person.getPersonHouseNumber()),
                () -> assertEquals("B", person.getPersonHouseNumberAdd()),
                () -> assertEquals("Amsterdam", person.getPersonCity()),
                () -> assertEquals("1234AB", person.getPersonZipcode())
        );
    }
}
