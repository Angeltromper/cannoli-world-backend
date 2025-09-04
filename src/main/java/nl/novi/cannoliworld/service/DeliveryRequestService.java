package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.dtos.CreateDeliveryRequestDto;
import nl.novi.cannoliworld.dtos.DeliveryRequestStatusDto;
import nl.novi.cannoliworld.models.DeliveryRequest;

import java.util.List;

public interface DeliveryRequestService {
    List<DeliveryRequest> getDeliveryRequests();                 // Admin
    List<DeliveryRequest> getMyDeliveryRequests(String username); // Klant
    DeliveryRequest getDeliveryRequest(Long id);                  // Detail admin/eigenaar
    DeliveryRequest createDeliveryRequest(CreateDeliveryRequestDto dto,String username);
    void updateDeliveryRequest(Long id, DeliveryRequestStatusDto statusDto);
    void deleteDeliveryRequest(Long id);
}

















