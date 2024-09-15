package nl.novi.cannoliworld.dtos;

import lombok.Data;
import nl.novi.cannoliworld.models.Status;

@Data
public class DeliveryRequestStatusDto {

    private Long id;

    private Status status;

}

