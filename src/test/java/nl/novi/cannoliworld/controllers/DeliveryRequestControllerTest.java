package nl.novi.cannoliworld.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.cannoliworld.dtos.CreateDeliveryRequestDto;
import nl.novi.cannoliworld.dtos.DeliveryRequestStatusDto;
import nl.novi.cannoliworld.models.CannoliItem;
import nl.novi.cannoliworld.models.DeliveryRequest;
import nl.novi.cannoliworld.models.DeliveryRequestStatus;
import nl.novi.cannoliworld.models.Person;
import nl.novi.cannoliworld.security.DeliveryRequestSecurity;
import nl.novi.cannoliworld.service.DeliveryRequestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DeliveryRequestController.class)
class DeliveryRequestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    DeliveryRequestService deliveryRequestService;

    // SpEL security bean die in @PreAuthorize wordt gebruikt
    @MockBean(name = "deliveryRequestSecurity")
    DeliveryRequestSecurity deliveryRequestSecurity;

    // === helpers ===
    private DeliveryRequest sampleEntity(long id) {
        DeliveryRequest dr = new DeliveryRequest();
        dr.setId(id);
        dr.setStatus(DeliveryRequestStatus.NEW);
        dr.setComment("Graag vrijdag leveren");

        CannoliItem item1 = new CannoliItem();
        item1.setArtikelnummer(1001L);
        item1.setNaam("Bosvruchten 35gr");
        item1.setPrijs(0.67);
        item1.setQty(4);

        CannoliItem item2 = new CannoliItem();
        item2.setArtikelnummer(1011L);
        item2.setNaam("Nocciola 35gr");
        item2.setPrijs(0.67);
        item2.setQty(1);

        dr.setCannoliList(List.of(item1, item2));

        Person applier = new Person();
        applier.setId(42L);
        dr.setApplier(applier);

        return dr;
    }

    private String validCreateBody() throws Exception {
        CreateDeliveryRequestDto dto = new CreateDeliveryRequestDto();
        CreateDeliveryRequestDto.ItemDto i1 = new CreateDeliveryRequestDto.ItemDto();
        i1.setCannoliId(1001L);
        i1.setQuantity(3);
        dto.setItems(List.of(i1));
        dto.setComment("Opmerking");
        return objectMapper.writeValueAsString(dto);
    }

    // === tests ===

    @Test
    @DisplayName("GET /deliveryRequests/all (ADMIN) → 200 en lijst")
    @WithMockUser(roles = "ADMIN")
    void getAll_admin_ok() throws Exception {
        given(deliveryRequestService.getDeliveryRequests())
                .willReturn(List.of(sampleEntity(1), sampleEntity(2)));

        mockMvc.perform(get("/deliveryRequests/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].status", is("NEW")));

        verify(deliveryRequestService).getDeliveryRequests();
    }

    @Test
    @DisplayName("GET /deliveryRequests/mine (ingelogd) → 200 en lijst")
    @WithMockUser(username = "hans", roles = "CUSTOMER")
    void mine_authenticated_ok() throws Exception {
        given(deliveryRequestService.getMyDeliveryRequests("hans"))
                .willReturn(List.of(sampleEntity(3)));

        mockMvc.perform(get("/deliveryRequests/mine"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(3)));

        verify(deliveryRequestService).getMyDeliveryRequests("hans");
    }

    @Test
    @DisplayName("GET /deliveryRequests/{id} (ADMIN) → 200 en detail")
    @WithMockUser(roles = "ADMIN")
    void getOne_admin_ok() throws Exception {
        given(deliveryRequestService.getDeliveryRequest(3L))
                .willReturn(sampleEntity(3));

        mockMvc.perform(get("/deliveryRequests/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.status", is("NEW")))
                .andExpect(jsonPath("$.cannoliList", hasSize(2)));

        verify(deliveryRequestService).getDeliveryRequest(3L);
    }

    @Test
    @DisplayName("POST /deliveryRequests/create (CUSTOMER) → 201 en Location header")
    @WithMockUser(username = "hans", roles = {"CUSTOMER"})
    void create_authenticated_created() throws Exception {
        given(deliveryRequestService.createDeliveryRequest(any(CreateDeliveryRequestDto.class), eq("hans")))
                .willReturn(sampleEntity(10));

        mockMvc.perform(post("/deliveryRequests/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validCreateBody()))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/deliveryRequests/10"))
                .andExpect(jsonPath("$.id", is(10)));

        verify(deliveryRequestService)
                .createDeliveryRequest(ArgumentMatchers.any(CreateDeliveryRequestDto.class), eq("hans"));
    }

    @Test
    @DisplayName("PUT /deliveryRequests/{id} (ADMIN) → 200 en service aangeroepen")
    @WithMockUser(roles = "ADMIN")
    void updateStatus_admin_ok() throws Exception {
        DeliveryRequestStatusDto body = new DeliveryRequestStatusDto();
        body.setStatus(DeliveryRequestStatus.CONFIRMED);

        mockMvc.perform(put("/deliveryRequests/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());

        verify(deliveryRequestService).updateDeliveryRequest(eq(7L), any(DeliveryRequestStatusDto.class));
    }

    @Test
    @DisplayName("DELETE /deliveryRequests/{id} (ADMIN) → 204")
    @WithMockUser(roles = "ADMIN")
    void delete_admin_noContent() throws Exception {
        mockMvc.perform(delete("/deliveryRequests/7"))
                .andExpect(status().isNoContent());

        verify(deliveryRequestService).deleteDeliveryRequest(7L);
    }
}
