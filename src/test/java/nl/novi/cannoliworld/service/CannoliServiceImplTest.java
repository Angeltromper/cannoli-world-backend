package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.exeptions.RecordNotFoundException;
import nl.novi.cannoliworld.models.Cannoli;
import nl.novi.cannoliworld.repositories.CannoliRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CannoliServiceImplTest {

    @Mock
    private CannoliRepository cannoliRepository;

    @InjectMocks
    private CannoliServiceImpl cannoliService;

    @Test
    @DisplayName("Should return the cannoli when the cannoli is exists")
    void getProductWhenProductExists() {
        Cannoli cannoli = new Cannoli();
        cannoli.setId(1L);
        cannoli.setCannoliName("test");
        cannoli.setCannoliType("test");
        cannoli.setDescription("test");
        cannoli.setIngredients("test");
        cannoli.setPrice(1.0);

        when(cannoliRepository.findById(1L)).thenReturn(Optional.of(cannoli));

        Cannoli result = cannoliService.getCannoli(1L);

        assertEquals(cannoli, result);
    }

    @Test
    @DisplayName("Should throw an exception when the cannoli does not exist")
    void getProductWhenProductDoesNotExistThenThrowException() {
        when(cannoliRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> cannoliService.getCannoli(1L));

        verify(cannoliRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should returns all cannolis")
    void getProductsShouldReturnsAllCannolis() {
        Cannoli cannoli = new Cannoli();
        cannoli.setId(1L);
        cannoli.setCannoliName("test");
        cannoli.setCannoliType("test");
        cannoli.setDescription("test");
        cannoli.setIngredients("test");
        cannoli.setPrice(1.0);

        when(cannoliRepository.findAll()).thenReturn(List.of(cannoli));

        List<Cannoli> cannolis = cannoliService.getCannolis();

        assertNotNull(cannolis);
        assertFalse(cannolis.isEmpty());
        assertEquals(1, cannolis.size());

        verify(cannoliRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should returns a list of cannolis when the cannoli type is found")
    void findProductListByTypeWhenProductTypeIsFoundThenReturnsAListOfProducts() {
        String cannoliType = "glutenfree";
        Cannoli cannoli = new Cannoli();
        cannoli.setCannoliType(cannoliType);

        when(cannoliRepository.findByCannoliTypeContainingIgnoreCase(cannoliType))
                .thenReturn(List.of(cannoli));

        var result = cannoliService.findCannoliListByType(cannoliType);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(cannoliType, result.get(0).getCannoliType());
    }

    @Test
    @DisplayName("Should throws an exception when the cannoli type is not found")
    void findCannoliListByTypeWhenCannoliTypeIsNotFoundThenThrowsException() {
        String cannoliType = "glutenfree";

        assertThrows(
                RecordNotFoundException.class,
                () -> cannoliService.findProductListByType(cannoliType));

        verify(cannoliRepository, times(1)).findByCannoliTypeContainingIgnoreCase(cannoliType);
    }

    @Test
    @DisplayName("Should delete the cannoli when the cannoli exists")
    void deleteProductWhenProductExists() {
        Cannoli cannoli1 = new Cannoli();
        cannoli1.setId(1L);
        cannoli1.setCannoliName("test");

        cannoliRepository.delete(cannoli1);

        cannoliService.deleteCannoli(1L);

        verify(cannoliRepository).delete(cannoli1);

    }

    @Test
    @DisplayName("Should update the cannoli when the cannoli exists")
    void updateProductWhenProductExists() {
        Cannoli cannoli1 = new Cannoli();
        cannoli1.setId(1L);
        cannoli1.setCannoliName("test");
        cannoli1.setCannoliType("test");
        cannoli1.setDescription("test");
        cannoli1.setIngredients("test");
        cannoli1.setPrice(1);
        when(cannoliRepository.findById(1L)).thenReturn(Optional.of(cannoli1));

        cannoli1.setCannoliName("");
        cannoliService.updateCannoli(cannoli1);

        verify(cannoliRepository).save(cannoli1);

        assertThat(cannoli1.getId()).isEqualTo(1);
        assertThat(cannoli1.getCannoliName()).isEqualTo("");

    }

    @Test
    @DisplayName("Should throw an exception when the cannoli does not exist")
    void updateCannoliWhenCannoliDoesNotExistThenThrowException() {
        Cannoli cannoli = new Cannoli();
        cannoli.setId(1L);
        cannoli.setCannoliName("test");
        cannoli.setCannoliType("test");
        cannoli.setDescription("test");
        cannoli.setIngredients("test");

        when(cannoliRepository.findById(1L)).thenReturn(null);

        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> {
                    cannoliService.updateCannoli(cannoli);
                });
    }

    @Test
    @DisplayName("Should create a cannoli when the cannoli is valid")
    void createProductWhenProductIsValid() {
        Cannoli cannoli = new Cannoli();
        cannoli.setId(1L);
        cannoli.setCannoliName("test");
        cannoli.setCannoliType("test");
        cannoli.setDescription("test");
        cannoli.setIngredients("test");
        cannoli.setPrice(1.0);

        when(cannoliRepository.save(cannoli)).thenReturn(cannoli);

        Cannoli result = cannoliService.createCannoli(cannoli);

        assertEquals(cannoli, result);
    }
}







