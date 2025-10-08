package nl.novi.cannoliworld.dtos;

import nl.novi.cannoliworld.models.Person;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PersonInputDto {
    public Long id;

    @NotBlank @Size(max = 100)
    public String personFirstname;

    @NotBlank @Size(max = 100)
    public String personLastname;

    @Size(max = 100)
    public String personStreetName;

    @Size(max = 10)
    public String personHouseNumber;

    @Size(max = 10)
    public String personHouseNumberAdd;

    @Size(max = 100)
    public String personCity;

    @Pattern(regexp = "^[1-9][0-9]{3}\\s?[A-Za-z]{2}$", message = "Ongeldige postcode")
    public String personZipcode;

    public Person toPerson() {
        var person = new Person();
        person.setId(id);
        person.setPersonFirstname(personFirstname);
        person.setPersonLastname(personLastname);
        person.setPersonStreetName(personStreetName);
        person.setPersonHouseNumber(personHouseNumber);
        person.setPersonHouseNumberAdd(personHouseNumberAdd);
        person.setPersonCity(personCity);
        person.setPersonZipcode(personZipcode);
        return person;
    }
}
