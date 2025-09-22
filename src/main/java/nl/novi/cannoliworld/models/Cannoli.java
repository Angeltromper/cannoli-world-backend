package nl.novi.cannoliworld.models;

import javax.persistence.*;

@Entity
@Table(name = "cannolis")
public class Cannoli {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cannoli_id")
    private Long id;

    @Column(name = "cannoli_name", length = 1000, nullable = false)
    private String cannoliName;

    @Column(name = "cannoli_type")
    private String cannoliType;

    @Column(name = "cannoli_description", length = 1000)
    private String description;

    @Column(name = "cannoli_ingredients", length = 1000)
    private String ingredients;

    @Column(name = "cannoli_price", nullable = false)
    private double price;

//    @OneToOne
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "image_file_name",
            referencedColumnName = "file_name",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private FileUploadResponse image;
    // Constructors
    public Cannoli() {}

    public Cannoli(String cannoliName, String cannoliType, String description, String ingredients, double price) {
        this.cannoliName = cannoliName;
        this.cannoliType = cannoliType;
        this.description = description;
        this.ingredients = ingredients;
        this.price = price;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCannoliName() { return cannoliName; }
    public void setCannoliName(String cannoliName) { this.cannoliName = cannoliName; }

    public String getCannoliType() { return cannoliType; }
    public void setCannoliType(String cannoliType) { this.cannoliType = cannoliType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public FileUploadResponse getImage() { return image; }
    public void setImage(FileUploadResponse image) { this.image = image; }
}
