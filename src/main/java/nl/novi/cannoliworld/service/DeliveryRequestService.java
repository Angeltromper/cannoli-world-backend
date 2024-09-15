package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.dtos.DeliveryRequestInputDto;
import nl.novi.cannoliworld.dtos.DeliveryRequestStatusDto;
import nl.novi.cannoliworld.models.DeliveryRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeliveryRequestService {

    List<DeliveryRequest> getDeliveryRequests();

    DeliveryRequest getDeliveryRequest(Long id);

    // DeliveryRequest createDeliveryRequest(DeliveryRequest deliveryRequest);

    DeliveryRequest createDeliveryRequest(DeliveryRequestInputDto deliveryRequestInputDto);

    void updateDeliveryRequest(DeliveryRequestStatusDto deliveryRequestStatusDto);

    void deleteDeliveryRequest(Long id);

}



