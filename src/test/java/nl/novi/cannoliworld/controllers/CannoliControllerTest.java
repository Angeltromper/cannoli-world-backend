package nl.novi.cannoliworld.controllers;

import nl.novi.cannoliworld.dtos.CannoliDto;
import nl.novi.cannoliworld.dtos.CannoliInputDto;
import nl.novi.cannoliworld.models.Cannoli;
import nl.novi.cannoliworld.service.CannoliService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CannoliControllerTest {

    @Mock
    private CannoliService cannoliService;

    @InjectMocks
    private CannoliController controller;

    @Test
    @DisplayName("Should return all products when no parameters are passed")
    void getCannolis_noParams_returnsAll() {
        Cannoli c = new Cannoli();
        c.setId(1L);
        c.setCannoliName("Test");
        c.setCannoliType("Test");
        c.setDescription("Test");
        c.setIngredients("Test");
        c.setPrice(1.0);

        when(cannoliService.getCannolis()).thenReturn(List.of(c));

        List<CannoliDto> result = controller.getCannolis(null, null);

        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getCannoliName());
    }

    @Test
    @DisplayName("Should create a cannoli when the cannoli is valid")
    void createCannoli_valid_returns201AndBody() {
        CannoliInputDto in = new CannoliInputDto();
        in.setCannoliName("test");
        in.setCannoliType("type");
        in.setDescription("desc");
        in.setIngredients("ing");
        in.setPrice(1f);

        Cannoli saved = new Cannoli();
        saved.setId(10L);
        saved.setCannoliName("test");
        saved.setCannoliType("type");
        saved.setDescription("desc");
        saved.setIngredients("ing");
        saved.setPrice(1f);

        when(cannoliService.createCannoli(any())).thenReturn(saved);

        ResponseEntity<CannoliDto> resp = controller.createCannoli(in);

        assertEquals(201, resp.getStatusCodeValue());
        assertNotNull(resp.getBody());
        assertEquals("test", resp.getBody().getCannoliName());
    }

    @Test
    @DisplayName("Should throw when the cannoli is invalid")
    void createCannoli_invalid_throws() {
        CannoliInputDto in = new CannoliInputDto();
        in.setCannoliName("");
        in.setCannoliType("");
        in.setDescription("");
        in.setIngredients("");
        in.setPrice(0f);

        when(cannoliService.createCannoli(any())).thenThrow(new IllegalArgumentException("invalid"));

        assertThrows(IllegalArgumentException.class, () -> controller.createCannoli(in));
    }
}


