package nl.novi.cannoliworld.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import nl.novi.cannoliworld.models.Status;

import java.util.List;

@Data
public class DeliveryRequestInputDto {

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Long> cannoliList;
    private String comment;

    private Status status;
    private Long applier;

    public List<Long> getCannoliList() { return cannoliList; }
    public String getComment() { return comment; }

    public Long getApplier() { return applier; }

    public Status getStatus() { return status; }
}
