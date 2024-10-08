package nl.novi.cannoliworld.models;

import java.util.Set;
import java.util.HashSet;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String emailAdress;

    @Column(nullable = false, length = 255)
    private String password;

    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getEmailAdress() { return emailAdress; }

    public void setEmailAdress(String emailAdress) { this.emailAdress = emailAdress; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public Set<Authority> getAuthorities() { return authorities; }

    public static void addAuthority(Authority authority) { this.authorities.add(authority);}

    public void removeAuthority(Authority authority) { this.authorities.remove(authority); }

}











