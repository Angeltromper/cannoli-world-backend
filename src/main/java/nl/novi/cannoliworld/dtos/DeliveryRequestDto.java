package nl.novi.cannoliworld.dtos;
import lombok.Data;
import nl.novi.cannoliworld.models.CannoliItem;
import nl.novi.cannoliworld.models.DeliveryRequest;
import nl.novi.cannoliworld.models.DeliveryRequestStatus;

import java.util.List;

@Data
public class DeliveryRequestDto {
    private Long id;
    private List<CannoliItem> cannoliList;        // JSONB-snapshot (id/naam/prijs/qty)
    private DeliveryRequestStatus status;         // top-level enum
    private String comment;
    private PersonDto applier;                    // aanvrager (klant)
    private PersonDto deliverer;                  // optioneel

    public static DeliveryRequestDto fromDeliveryRequest(DeliveryRequest dr) {
        DeliveryRequestDto dto = new DeliveryRequestDto();
        dto.setId(dr.getId());
        dto.setCannoliList(dr.getCannoliList());
        dto.setStatus(dr.getStatus());
        dto.setComment(dr.getComment());
        if (dr.getApplier() != null) {
            dto.setApplier(PersonDto.fromPerson(dr.getApplier()));
        }
        if (dr.getDeliverer() != null) {
            dto.setDeliverer(PersonDto.fromPerson(dr.getDeliverer()));
        }
        return dto;
    }
}
