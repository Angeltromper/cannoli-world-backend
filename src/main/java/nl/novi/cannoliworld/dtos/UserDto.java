package nl.novi.cannoliworld.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDto {

    @NotBlank
    private String username;
    @Email
    @NotBlank
    @JsonProperty("email")
    private String emailAddress;
    @NotBlank
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

