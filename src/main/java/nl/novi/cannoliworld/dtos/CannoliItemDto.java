package nl.novi.cannoliworld.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CannoliItemDto {
    private Long cannoliId;
    private String name;
    private String type;
    private String description;
    private String ingredients;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal lineTotal;
}

