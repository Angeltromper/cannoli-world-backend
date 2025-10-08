package nl.novi.cannoliworld.payload;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = "Username is verplicht")
    private String username;

    @NotBlank(message = "Password is verplicht")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
