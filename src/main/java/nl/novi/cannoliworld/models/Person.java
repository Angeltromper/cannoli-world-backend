package nl.novi.cannoliworld.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Person {



    @Getter
    @GeneratedValue
    @Id
    Long id;

    @Setter
    @Getter
    String personFirstname;
    @Setter
    @Getter
    String personLastname;
    @Setter
    @Getter
    String personStreetName;
    @Setter
    @Getter
    String personHouseNumber;
    @Getter
    @Setter
    String personHouseNumberAdd;
    @Getter
    @Setter
    String personCity;
    @Getter
    @Setter
    String personZipcode;

    @OneToOne(mappedBy = "person")
    User user;

    @OneToMany(mappedBy = "applier")
    private List<DeliveryRequest> applier;

    @OneToMany(mappedBy = "deliverer")
    private List<DeliveryRequest> deliverer;

    public Person() {
    }

    public void setId(Long id) {
    }
}

