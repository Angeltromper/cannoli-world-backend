package nl.novi.cannoliworld.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@IdClass(AuthorityKey.class)
@Table(name = "authorities")

public class Authority implements Serializable {

    @Id
    @Column(nullable = false)
    private String username;

    @Id
    @Column(nullable = false)
    private String authority;

    public Authority() {

    }
    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

}
