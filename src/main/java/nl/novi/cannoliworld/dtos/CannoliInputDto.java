package nl.novi.cannoliworld.dtos;

import lombok.Data;
import nl.novi.cannoliworld.models.Cannoli;

@Data
public class CannoliInputDto {

  public Long id;
  public String cannoliName;
  public String cannoliType;
  public String description;
  public String ingredients;
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









