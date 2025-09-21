package nl.novi.cannoliworld.dtos;

import nl.novi.cannoliworld.models.DeliveryRequestStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DeliveryRequestStatusDto {

    @NotNull
    private DeliveryRequestStatus status;

    @Size(max = 500)
    private String note;

    public DeliveryRequestStatusDto() {}

    public DeliveryRequestStatusDto(DeliveryRequestStatus status, String note) {
        this.status = status;
        this.note = note;
    }

    public DeliveryRequestStatus getStatus() {
        return status;
    }
    public void setStatus(DeliveryRequestStatus status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
}
