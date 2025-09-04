package nl.novi.cannoliworld.dtos;

import java.math.BigDecimal;

public class CannolItemDto {

    private Long cannoliId;
    private String name;
    private String type;         // optioneel
    private String description;  // optioneel
    private String ingredients;  // optioneel
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal lineTotal;


    public CannolItemDto() {
    }
}
