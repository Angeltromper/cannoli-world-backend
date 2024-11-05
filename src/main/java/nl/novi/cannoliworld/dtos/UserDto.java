package nl.novi.cannoliworld.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.novi.cannoliworld.models.Authority;
import nl.novi.cannoliworld.models.Person;

import java.util.Set;

public class UserDto {

    public String username;
    public String emailAdress;

    @JsonSerialize
    public Set<Authority> authorities;

    public String password;

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getEmailAdress() { return emailAdress; }

    public void setEmailAdress(String emailAdress) { this.emailAdress = emailAdress; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public Set<Authority>  getAuthorities() { return authorities; }

    public void setAuthorities(Set<Authority> authorities) { this.authorities = authorities; }

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

