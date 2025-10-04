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
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void getCannolisWhenParametersArePassed() {
        var cannoli = new Cannoli();
        cannoli.setId(1L);
        cannoli.setCannoliName("Test");
        cannoli.setCannoliType("Test");
        cannoli.setDescription("Test");
        cannoli.setIngredients("Test");
        cannoli.setPrice(1.0);

        var cannolis = List.of(cannoli);

        when(cannoliService.getCannolis()).thenReturn(cannolis);

        var result = controller.getCannolis(null, null);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should create a cannoli when the cannoli is valid")
    void createCannoliWhenCannoliIsValid() {
        CannoliInputDto cannoliInputDto = new CannoliInputDto();
        cannoliInputDto.setCannoliName("test");
        cannoliInputDto.setCannoliType("test");
        cannoliInputDto.setDescription("test");
        cannoliInputDto.setIngredients("test");
        cannoliInputDto.setPrice(1f);

        Cannoli cannoli = new Cannoli();
        cannoli.setCannoliName("test");
        cannoli.setCannoliType("test");
        cannoli.setDescription("test");
        cannoli.setIngredients("test");
        cannoli.setPrice(1f);

        when(cannoliService.createCannoli(any())).thenReturn(cannoli);

        CannoliDto result = controller.createCannoli(cannoliInputDto).getBody();

        assertEquals(cannoliInputDto.getCannoliName(), result.getCannoliName());
    }

    @Test
    @DisplayName("Should throw an exception when the cannoli is invalid")
    void createCannoliWhenCannoliIsInvalidThenThrowException() {
        CannoliInputDto cannoliInputDto = new CannoliInputDto();
        cannoliInputDto.setCannoliName("");
        cannoliInputDto.setCannoliType("");
        cannoliInputDto.setDescription("");
        cannoliInputDto.setIngredients("");
        cannoliInputDto.setPrice(0f);

        assertThrows(
               NullPointerException.class,
                () -> controller.createCannoli(cannoliInputDto));
    }
}







