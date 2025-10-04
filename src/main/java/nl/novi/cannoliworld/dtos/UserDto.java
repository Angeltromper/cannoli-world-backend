package nl.novi.cannoliworld.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDto {

    @NotBlank(message = "Gebruikersnaam is verplicht")
    @Size(min = 3, max = 32, message = "Gebruikersnaam moet 3-32 tekens zijn")
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z0-9._-]*$",
            message = "Moet met een letter beginnen; alleen letters, cijfers, ., _ en -"
    )
    private String username;
    @NotBlank(message = "E-mailadres is verplicht")
    @Email(message = "Ongeldig e-mailadres")
    @JsonProperty("email")
    private String emailAddress;
    @NotBlank
    @Size(min = 8, max = 64, message = "Wachtwoord moet minimaal 8 tekens zijn")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    public UserDto() {}
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

