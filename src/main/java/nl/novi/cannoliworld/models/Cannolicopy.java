/*

package nl.novi.cannoliworld.models;

import org.hibernate.annotations.TypeDef;
import javax.persistence.*;

@Entity


@TypeDef(name = "json", typeClass = JsonStringType.class)


@Table(name = "cannolis")


public class Cannolicopy {

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

    public Long getId() { return id; }

    public String getCannoliName() { return cannoliName; }

    public String getCannoliType() { return cannoliType; }

    public String getDescription() { return description; }

    public String getIngredients() { return ingredients; }

    public double getPrice() { return price; }

    public FileUploadResponse getImage()  { return image; }
    public void setImage(FileUploadResponse image) { this.image = image; }

    public void setId(Long id) { this.id = id; }

    public void setCannoliName(String cannoliName) { this.cannoliName = cannoliName; }

    public void setCannoliType(String cannoliType) { this.cannoliType = cannoliType; }

    public void setDescription(String description) { this.description = description; }

    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public void setPrice(double price) { this.price = price; }
}



*/










