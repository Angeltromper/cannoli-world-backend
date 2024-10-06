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

    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false, unique = true)
    private String emailAdress;

    @Column
    private String apikey;

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
    private Set<Authority> authorities = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getEmailAdress() { return emailAdress; }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public Person getPerson() { return person; }

    public void setPerson(Person person) { this.person = person; }

    public Long getId() { return id; }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void removeAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public FileUploadResponse getImage() {
        return image;
    }

    public void setImage(FileUploadResponse image) {
        this.image = image;
    }

    public void setApiKey(String number) {
    }

    public boolean IsEnabled() {
        return false;
    }

    public void setId(Long id) { this.id = id; }

}








