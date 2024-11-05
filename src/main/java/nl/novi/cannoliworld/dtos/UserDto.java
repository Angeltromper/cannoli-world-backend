package nl.novi.cannoliworld.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import nl.novi.cannoliworld.models.Authority;
import nl.novi.cannoliworld.models.Person;

import java.util.Set;

@Setter
@Getter
public class UserDto {

    public String username;
    public String emailAdress;

    @JsonSerialize
    public Set<Authority> authorities;

    public String password;

    public UserDto() {
    }

    public boolean isEmpty() {
        return false;
    }

    public UserDto get() {
        return null;
    }

    public void setId(long l) {
    }

    public void setPerson(Person person) {
    }

    public void setApiKey(String randomString) {
    }

    public void addAuthority(Authority roleUser) {
    }
}

