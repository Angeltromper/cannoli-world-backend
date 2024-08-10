package nl.novi.cannoliworld.dtos;

import nl.novi.cannoliworld.models.Person;



public class PersonInputDto {

    public Long id;
    public String personFirstname;
    public String personLastname;
    public String personStreetName;
    public String personHouseNumber;
    public String personHouseNumberAdd;
    public String personCity;
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
