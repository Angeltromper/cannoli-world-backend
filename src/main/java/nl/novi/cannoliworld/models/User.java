package nl.novi.cannoliworld.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name= "users")
public class User implements UserDetails {

    @Id
    @Column(nullable = false,
            unique = true)
    private String username;

    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;
//    private boolean enabled = true;

    @Column
    private String apikey;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToOne
    Person person;

    @OneToOne
    FileUploadResponse image;

    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> roles = new HashSet<>();

    public String getUsername() { return username; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setUsername(String username) { this.username = username; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

   public boolean IsEnabled() {return false;}

    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public String getApikey() { return apikey; }

    public void setApikey(String apikey) { this.apikey = apikey; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public Person getPerson() { return person; }

    public void setPerson(Person person) { this.person = person; }

    public Set<Authority> getRoles() { return roles; }

    public void addAuthority(Authority authority) { this.roles.add(authority); }

    public void removeAuthority(Authority authority) { this.roles.remove(authority); }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public FileUploadResponse getImage() { return image; }

    public void setImage(FileUploadResponse image) { this.image = image; }

}








