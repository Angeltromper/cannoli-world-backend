package nl.novi.cannoliworld.dtos;

import lombok.Data;
import nl.novi.cannoliworld.models.Cannoli;

import javax.validation.constraints.*;

@Data
public class CannoliInputDto {

    @NotBlank
    @Size(max = 255)
    public String cannoliName;

    @NotBlank
    @Size(max = 100)
    public String cannoliType;

    @NotBlank
    @Size(max = 2000)
    public String description;

    @NotBlank
    @Size(max = 2000)
    public String ingredients;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    public Float price;

    public Cannoli toCannoli() {
        var cannoli = new Cannoli();
        cannoli.setCannoliName(cannoliName);
        cannoli.setCannoliType(cannoliType);
        cannoli.setDescription(description);
        cannoli.setIngredients(ingredients);
        cannoli.setPrice(price);
        return cannoli;
    }
}
