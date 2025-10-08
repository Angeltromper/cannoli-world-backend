package nl.novi.cannoliworld.models;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String username;
    @Column(nullable = false, length = 100)
    private String authority;
    public Authority() {}
    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getAuthority() { return authority; }

    public void setUsername(String username) { this.username = username; }
    public void setAuthority(String authority) { this.authority = authority; }
}
