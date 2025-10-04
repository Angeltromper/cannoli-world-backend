package nl.novi.cannoliworld.dtos;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CannoliOrderItemDto {
    private Long cannoliId;
    private String naam;
    private BigDecimal prijs;
    private int qty;
}
