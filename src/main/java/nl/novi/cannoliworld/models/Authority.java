package nl.novi.cannoliworld.models;

import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK naar User.username (User heeft @Id String username)
    @Column(nullable = false)
    private String username;

    // bv. "ROLE_USER", "ROLE_ADMIN"
    @Column(nullable = false)
    private String authority;

    public Authority() {}
    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    @Override
    public String getAuthority() { return authority; }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public void setAuthority(String authority) { this.authority = authority; }
}
