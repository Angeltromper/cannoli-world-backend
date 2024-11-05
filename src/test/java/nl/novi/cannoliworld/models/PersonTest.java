package nl.novi.cannoliworld.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest {

    @Test
    @DisplayName("Should set the zipcode")
    void setPersonZipcodeShouldSetTheZipcode() {
        Person person = new Person();
        person.setPersonZipcode("123");
        assertEquals("123", person.getPersonZipcode());
    }


    @Test
    @DisplayName("Should return the zipcode of the person")
    void setPersonZipcodeShouldReturnTheZipcodeOfThePerson() {
        Person person = new Person();
        person.setPersonZipcode("123");
        assertEquals("123", person.getPersonZipcode());
    }

    @Test
    @DisplayName("Should set the city")
    void setPersonCityShouldSetTheCity() {
        Person person = new Person();
        person.setPersonCity("Amsterdam");
        assertEquals("Amsterdam", person.getPersonCity());
    }

    @Test
    @DisplayName("Should return the city of the person")
    void getPersonCityShouldReturnTheCityOfThePerson() {
        Person person = new Person();
        person.setPersonCity("Amsterdam");
        assertEquals("Amsterdam", person.getPersonCity());
    }

    @Test
    @DisplayName("Should set the personHouseNumberAdd")
    void setPersonHouseNumberAdd() {
        Person person = new Person();
        person.setPersonHouseNumberAdd("B");
        assertEquals("B", person.getPersonHouseNumberAdd());
    }

    @Test
    @DisplayName("Should return the personHouseNumberAdd")
    void setPersonHouseNumberAddShouldReturnThePersonHouseNumberAdd() {
        Person person = new Person();
        person.setPersonHouseNumberAdd("B");
        assertEquals("B", person.getPersonHouseNumberAdd());
    }

    @Test
    @DisplayName("Should return the personHouseNumber")
    void setPersonHouseNumber() {
        Person person = new Person();
        person.setPersonHouseNumber("7");
        assertEquals("7", person.getPersonHouseNumber());
    }

    @Test
    @DisplayName("Should return the person house number")
    void setPersonHouseNumberShouldReturnThePersonHouseNumber() {
        Person person = new Person();
        person.setPersonHouseNumber("7");
        assertEquals("7", person.getPersonHouseNumber());
    }

    @Test
    @DisplayName("Should return the person street name")
    void getPersonStreetNameShouldReturnTheStreetName() {
        Person person = new Person();
        person.setPersonStreetName("TestStreet");
        assertEquals("TestStreet", person.getPersonStreetName());
    }

    @Test
    @DisplayName("Should set het lastname")
    void setPersonLastnamesShouldSetTheLastname() {
        Person person = new Person();
        person.setPersonLastname("test");
        assertEquals("test", person.getPersonLastname());
    }

    @Test
    @DisplayName("Should return the person lastname")
    void setPersonLastnamesShouldReturnThePersonLastname() {
        Person person = new Person();
        person.setPersonLastname("Test");
        assertEquals("Test", person.getPersonLastname());
    }

    @Test
    @DisplayName("Should set the firstname")
    void setPersonFirstnameShouldSetTheFirstname() {
        Person person = new Person();
        person.setPersonFirstname("Angel");
        assertEquals("Angel", person.getPersonFirstname());
    }

    @Test
    @DisplayName("Should return the firstname")
    void setPersonFirstnameShouldReturnThePersonFirstname() {
        Person person = new Person();
        person.setPersonFirstname("Angel");
        assertEquals("Angel", person.getPersonFirstname());
    }

    @Test
    @DisplayName("Should set the id")
    void setIdShouldSetTheId() {
        Person person = new Person();
        person.setId(1L);
        assertEquals(1L, person.getId());
    }

    @Test
    @DisplayName("Should return the id of the person")
    void getIdShouldReturnThe () {
        Person person = new Person();
        person.setId(1L);
        assertEquals(1L, person.getId());
    }
}
