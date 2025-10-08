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

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column
    private String apikey;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @OneToOne
    @JoinColumn(name = "person_id", unique = true)
    private Person person;

    @OneToOne
    @JoinColumn(name = "image_id")
    private FileUploadResponse image;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "username",
            referencedColumnName = "username",
            insertable = false,
            updatable = false
    )
    private Set<Authority> roles = new HashSet<>();
    public User() {}
    public Long getId() { return id;}
    public void setId(Long id) { this.id = id; }
    @Override
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    @Override
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
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
    public Set<Authority> getRoles() { return roles;}
    public void setRoles(Set<Authority> roles) { this.roles = roles; }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() { return roles; }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() { return true; }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() { return true; }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() { return true; }
}
