package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.dtos.DeliveryRequestInputDto;
import nl.novi.cannoliworld.exeptions.RecordNotFoundException;
import nl.novi.cannoliworld.models.Cannoli;
import nl.novi.cannoliworld.models.DeliveryRequest;
import nl.novi.cannoliworld.models.Status;
import nl.novi.cannoliworld.repositories.DeliveryRequestRepository;
import nl.novi.cannoliworld.repositories.CannoliRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryRequestServiceImplTest {

    @Mock
    private DeliveryRequestRepository deliveryRequestRepository;

    @Mock
    private CannoliRepository cannoliRepository;

    @InjectMocks
    private DeliveryRequestServiceImpl deliveryRequestService;

    @Test
    @DisplayName("Should return the delivery request when the delivery request exists")
    void getDeliveryRequestWhenDeliveryRequestExist() {
        DeliveryRequest deliveryRequest = new DeliveryRequest();
        deliveryRequest.setId(1L);
        deliveryRequest.setStatus(Status.AVAILABLE);

        when(deliveryRequestRepository.findById(1L)).thenReturn(Optional.of(deliveryRequest));

        DeliveryRequest result = deliveryRequestService.getDeliveryRequest(1L);

        assertEquals(1L, result.getId());
        assertEquals(Status.AVAILABLE, ((DeliveryRequest) result).getStatus());
    }

    @Test
    @DisplayName("Should throw an exception when the delivery request does not exist")
    void getDeliveryRequestWhenDeliveryRequestDoesNotExistThenThrowException() {
        when(deliveryRequestRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                RecordNotFoundException.class, () -> deliveryRequestService.getDeliveryRequest(1L));
    }

    @Test
    @DisplayName("Should return all delivery requests")
    void getDeliveryRequestsShouldReturnAllDeliveryRequests() {
        DeliveryRequest deliveryRequest = new DeliveryRequest();
        when(deliveryRequestRepository.findAll()).thenReturn(Arrays.asList(deliveryRequest));

        assertEquals(1, deliveryRequestService.getDeliveryRequests().size());
    }

    @Test
    @DisplayName("Should delete the delivery request when the id is found")
    void deleteDeliveryRequestWhenIdIsFound() {
        DeliveryRequest deliveryRequest = new DeliveryRequest();
        deliveryRequest.setId(1L);

        deliveryRequestRepository.delete(deliveryRequest);

        deliveryRequestService.deleteDeliveryRequest(1L);

        verify(deliveryRequestRepository, times(1)).delete(deliveryRequest);
    }

    @Test
    @DisplayName("Should create a delivery request when the delivery request does not exist")
    void createDeliveryRequestWhenDeliveryRequestDoesNotExist() {
        DeliveryRequestInputDto deliveryRequestInputDto = new DeliveryRequestInputDto();

        Cannoli cannoli1 = new Cannoli();
        cannoli1.setId(1L);
        cannoli1.setCannoliName("cannoli1");
        cannoli1.setPrice(2.0);

        Cannoli cannoli2 = new Cannoli();
        cannoli2.setId(2L);
        cannoli2.setCannoliName("cannoli2");
        cannoli2.setPrice(3.0);

        deliveryRequestInputDto.setCannoliList(Arrays.asList(1L, 2L));
        deliveryRequestInputDto.setComment("comment");
        deliveryRequestInputDto.setStatus(Status.AVAILABLE);
        deliveryRequestInputDto.setApplier(1L);

        when(cannoliRepository.findById(1L)).thenReturn(Optional.of(cannoli1));
        when(cannoliRepository.findById(2L)).thenReturn(Optional.of(cannoli2));

        DeliveryRequest deliveryRequest =
                deliveryRequestService.createDeliveryRequest(deliveryRequestInputDto);
    }
}
