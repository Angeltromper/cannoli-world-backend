package nl.novi.cannoliworld.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity

public class User {


    @Setter
    @Id
    @Column(nullable = false,
            unique = true)
    private String username;

    @Setter
    @Column(nullable = false)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @Column(nullable = false)
    private boolean enabled = true;

    @Setter
    @Column
    private String apikey;

    @Setter
    @Column(nullable = false, unique = true)
    private String emailAdress;

    @Setter
    @OneToOne
    Person person;

    @Setter
    @OneToOne
    FileUploadResponse image;

    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    public User() {
    }

    public boolean IsEnabled() {
        return false;
    }
    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }
    public void removeAuthority(Authority authority) { this.authorities.remove(authority); }
}








