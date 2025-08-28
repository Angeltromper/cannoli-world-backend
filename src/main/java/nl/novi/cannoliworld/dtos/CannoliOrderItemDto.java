
package nl.novi.cannoliworld.dtos;

import lombok.Data;

@Data
public class CannoliOrderItemDto {
    private String artikelnummer;
    private Long cannoliId;
    private String naam;
//    private double prijs;
    private int qty;
}
