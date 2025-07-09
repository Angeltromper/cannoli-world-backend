package nl.novi.cannoliworld.dtos;

/*
import java.util.Map;
*/

import nl.novi.cannoliworld.models.DeliveryRequest;
import nl.novi.cannoliworld.models.Status;




public class DeliveryRequestDto {

    private Long id;
/*
    @SuppressWarnings("JpaAttributeTypeInspection")
    */

    private String cannoliList;
    private Status status;
    private String comment;
    private PersonDto applier;

    public static DeliveryRequestDto fromDeliveryRequest(DeliveryRequest deliveryRequest) {

        var dto = new DeliveryRequestDto();

        dto.setId(deliveryRequest.getId());

        dto.setCannoliList(deliveryRequest.getCannoliList());

        dto.setStatus(deliveryRequest.getStatus());

        dto.setComment(deliveryRequest.getComment());

        dto.setApplier(PersonDto.fromPerson(deliveryRequest.getApplier()));

        return dto;
    }

    public Long getId() { return id; }
    private void setId(Long id) { this.id = id; }

    public String getCannoliList() { return cannoliList; }
    private void setCannoliList(String cannoliList) { this.cannoliList = cannoliList; }

    public Status getStatus() { return status; }
    private void setStatus(Status status) { this.status = status; }

    public String getComment() { return comment; }
    private void setComment(String comment) { this.comment = comment; }

    public PersonDto getApplier() { return applier; }
    private void setApplier(PersonDto applier) { this.applier = applier;}
}





        