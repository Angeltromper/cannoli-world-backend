package nl.novi.cannoliworld.payload;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Setter
@Getter
@NoArgsConstructor

public class AuthenticationRequest {

    private String username;
    private String password;

    
    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}

