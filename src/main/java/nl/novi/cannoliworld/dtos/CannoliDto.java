package nl.novi.cannoliworld.dtos;
import lombok.Getter;
import lombok.Setter;
import nl.novi.cannoliworld.models.Cannoli;
import java.math.BigDecimal;

@Getter
@Setter
public class CannoliDto {
    private Long id;
    private String cannoliName;
    private String cannoliType;
    private String description;
    private String ingredients;
    private BigDecimal price;
    private ImageDto image;

    public CannoliDto() {}

    public static CannoliDto fromCannoli(Cannoli c) {
        var dto = new CannoliDto();
        dto.id = c.getId();
        dto.cannoliName = c.getCannoliName();
        dto.cannoliType = c.getCannoliType();
        dto.description = c.getDescription();
        dto.ingredients = c.getIngredients();

        // double → BigDecimal
        dto.price = BigDecimal.valueOf(c.getPrice());

        // voorkomt LazyInitializationException
        dto.image = (c.getImage() != null)
                ? new ImageDto(c.getImage().getUrl())
                : null;

        return dto;
    }
}
