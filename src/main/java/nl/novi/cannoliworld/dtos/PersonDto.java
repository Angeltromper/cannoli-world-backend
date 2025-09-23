package nl.novi.cannoliworld.dtos;
import lombok.Data;
import nl.novi.cannoliworld.models.Person;

@Data
public class PersonDto {
    private Long id;
    private String personFirstname;
    private String personLastname;
    private String personStreetName;
    private String personHouseNumber;
    private String personHouseNumberAdd;
    private String personZipcode;
    private String personCity;

    public PersonDto() {
    }
    public static PersonDto fromPerson(Person p) {
        PersonDto dto = new PersonDto();
        dto.setId(p.getId());
        dto.setPersonFirstname(p.getPersonFirstname());
        dto.setPersonLastname(p.getPersonLastname());
        dto.setPersonStreetName(p.getPersonStreetName());
        dto.setPersonHouseNumber(p.getPersonHouseNumber());
        dto.setPersonHouseNumberAdd(p.getPersonHouseNumberAdd());
        dto.setPersonZipcode(p.getPersonZipcode());
        dto.setPersonCity(p.getPersonCity());
        return dto;
    }
}
