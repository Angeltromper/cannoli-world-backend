package nl.novi.cannoliworld.models;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("DeliveryRequest")
class DeliveryRequestTest {

    @Test
    @DisplayName("Should return the status of the delivery request")
     void getStatusShouldReturnTheStatusOfTheDeliveryRequest() {
        DeliveryRequest deliveryRequest = new DeliveryRequest();
        deliveryRequest.setStatus(Status.AVAILABLE);
        assertEquals(Status.AVAILABLE, deliveryRequest.getStatus());
    }

    @Test
        @DisplayName("Should set the id")
        void setId() {
            DeliveryRequest deliveryRequest = new DeliveryRequest();
            deliveryRequest.setId(1L);
            assertEquals(1L, deliveryRequest.getId());
        }

        @Test
        @DisplayName("Should return the id of the delivery request")
        void getIdShouldReturnTheIdOfTheDeliveryRequest() {
            DeliveryRequest deliveryRequest = new DeliveryRequest();
            deliveryRequest.setId(1L);

            assertEquals(1L, deliveryRequest.getId());
        }

}








