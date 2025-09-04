package nl.novi.cannoliworld.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CannoliItemDto {
    private Long cannoliId;
    private String name;
    private String type;         // optioneel
    private String description;  // optioneel
    private String ingredients;  // optioneel
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal lineTotal;
}

