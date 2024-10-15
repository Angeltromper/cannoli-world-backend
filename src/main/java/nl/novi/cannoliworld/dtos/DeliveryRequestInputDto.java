package nl.novi.cannoliworld.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import nl.novi.cannoliworld.models.Status;

import java.util.List;


@Getter
@Data
public class DeliveryRequestInputDto {

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Long> cannoliList;
    private String comment;

    private Status status;
    private Long applier;

}
