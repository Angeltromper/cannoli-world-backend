package nl.novi.cannoliworld.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "cannolis")


public class Cannoli {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cannoli_id")
    public Long id;

    @Column(name = "cannoli_name",
            length = 1000)
    public String cannoliName;

    @Column(name = "cannoli_type")
    public String cannoliType;

    @Column(
            name = "cannoli_description",
            length = 1000)
    public String description;

    @Column(
            name = "cannoli_ingredients",
            length = 1000)
    public String ingredients;

    @Column(name = "cannoli_price")
    public double price;

    @OneToOne
    FileUploadResponse image;

    public Cannoli() {
    }

}

