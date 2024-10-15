package nl.novi.cannoliworld.dtos;

/*
import java.util.Map;
*/

import lombok.Getter;
import lombok.Setter;
import nl.novi.cannoliworld.models.DeliveryRequest;
import nl.novi.cannoliworld.models.Status;



@Setter
@Getter
public class DeliveryRequestDto {

    private Long id;
/*
    @SuppressWarnings("JpaAttributeTypeInspection")
    */

    private String cannoliList;
    private Status status;
    private String comment;
    private PersonDto applier;

    public DeliveryRequestDto() {
    }

    public static DeliveryRequestDto fromDeliveryRequest(DeliveryRequest deliveryRequest) {

        var dto = new DeliveryRequestDto();

        dto.setId(deliveryRequest.getId());

        dto.setCannoliList(deliveryRequest.getCannoliList());

        dto.setStatus(deliveryRequest.getStatus());

        dto.setComment(deliveryRequest.getComment());

        dto.setApplier(PersonDto.fromPerson(deliveryRequest.getApplier()));

        return dto;

    }

}

        