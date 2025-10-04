package nl.novi.cannoliworld.controllers;

import nl.novi.cannoliworld.models.DeliveryRequest;
import nl.novi.cannoliworld.security.DeliveryRequestSecurity;
import nl.novi.cannoliworld.service.DeliveryRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = DeliveryRequestController.class,
        excludeFilters = @Filter(
                type = FilterType.REGEX,
                pattern = "nl\\.novi\\.cannoliworld\\.(config|filter)\\..*"
        )
)
@AutoConfigureMockMvc
class DeliveryRequestControllerTest {

    @Autowired MockMvc mockMvc;

    @MockBean DeliveryRequestService deliveryRequestService;
    @MockBean DeliveryRequestSecurity deliveryRequestSecurity;

    @BeforeEach
    void stubSecurity() {
        // standaard: eigenaar-check altijd true
        given(deliveryRequestSecurity.isOwner(anyLong(), any())).willReturn(true);
    }

    private DeliveryRequest dr(long id) {
        DeliveryRequest d = new DeliveryRequest();
        d.setId(id);
        return d;
    }

    private String validCreateJson() {
        return """
        {
          "items": [ { "cannoliId": 1001, "quantity": 2 } ],
          "comment": "Graag vrijdag leveren"
        }
        """;
    }

    @Test
    @DisplayName("GET /deliveryRequests/all (ADMIN) → 200")
    void getAll_admin_ok() throws Exception {
        given(deliveryRequestService.getDeliveryRequests()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/deliveryRequests/all")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(deliveryRequestService).getDeliveryRequests();
    }

    @Test
    @DisplayName("GET /deliveryRequests/mine (USER) → 200")
    void mine_authenticated_ok() throws Exception {
        given(deliveryRequestService.getMyDeliveryRequests("hans"))
                .willReturn(Collections.emptyList());

        mockMvc.perform(get("/deliveryRequests/mine")
                        .with(user("hans").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(deliveryRequestService).getMyDeliveryRequests("hans");
    }

    @Test
    @DisplayName("GET /deliveryRequests/{id} (owner of ADMIN) → 200")
    void getOne_owner_ok() throws Exception {
        given(deliveryRequestService.getDeliveryRequest(7L)).willReturn(dr(7L));

        mockMvc.perform(get("/deliveryRequests/7")
                        .with(user("hans").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(deliveryRequestService).getDeliveryRequest(7L);
    }

    @Test
    @DisplayName("POST /deliveryRequests/create (USER) → 201 + Location")
    void create_authenticated_created() throws Exception {
        given(deliveryRequestService.createDeliveryRequest(any(), eq("hans")))
                .willReturn(dr(7L));

        mockMvc.perform(post("/deliveryRequests/create")
                        .with(user("hans").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validCreateJson()))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/deliveryRequests/7"));

        verify(deliveryRequestService).createDeliveryRequest(any(), eq("hans"));
    }

    @Test
    @DisplayName("PUT /deliveryRequests/{id} (ADMIN) → 200")
    void updateStatus_admin_ok() throws Exception {
        String body = "{ \"status\": \"CONFIRMED\" }";

        mockMvc.perform(put("/deliveryRequests/7")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        verify(deliveryRequestService).updateDeliveryRequest(eq(7L), any());
    }

    @Test
    @DisplayName("DELETE /deliveryRequests/{id} (ADMIN) → 204")
    void delete_admin_noContent() throws Exception {
        mockMvc.perform(delete("/deliveryRequests/7")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(deliveryRequestService).deleteDeliveryRequest(7L);
    }
}
