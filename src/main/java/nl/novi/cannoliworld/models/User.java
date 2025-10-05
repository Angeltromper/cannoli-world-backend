package nl.novi.cannoliworld.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonIgnore

    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column
    private String apikey;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToOne

    private Person person;

    @OneToOne
    private FileUploadResponse image;

    @OneToMany(
            targetEntity = Authority.class,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Set<Authority> roles = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    @Override @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() { return roles; }
    public Set<Authority> getRoles() { return roles; }
    public void addAuthority(Authority a) { roles.add(a); }
    public void removeAuthority(Authority a) { roles.remove(a); }

    @Override @JsonIgnore
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override @JsonIgnore public boolean isAccountNonExpired() { return true; }
    @Override @JsonIgnore public boolean isAccountNonLocked() { return true; }
    @Override @JsonIgnore public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public String getApikey() { return apikey; }
    public void setApikey(String apikey) { this.apikey = apikey; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }

    public FileUploadResponse getImage() { return image; }
    public void setImage(FileUploadResponse image) { this.image = image; }
}

