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

    private static Cannoli c(long id, String name, String type, String desc, String ingr, double price) {
        Cannoli x = new Cannoli();
        x.setId(id);
        x.setCannoliName(name);
        x.setCannoliType(type);
        x.setDescription(desc);
        x.setIngredients(ingr);
        x.setPrice(price);
        return x;
    }

    @Test
    @DisplayName("getCannoli — bestaat → entity terug")
    void getProductWhenProductExists() {
        var cannoli = c(1L, "test", "test", "test", "test", 1.0);
        when(cannoliRepository.findById(1L)).thenReturn(Optional.of(cannoli));

        Cannoli result = cannoliService.getCannoli(1L);

        assertEquals(cannoli, result);
        verify(cannoliRepository).findById(1L);
        verifyNoMoreInteractions(cannoliRepository);
    }

    @Test
    @DisplayName("getCannoli — bestaat niet → RecordNotFoundException")
    void getProductWhenProductDoesNotExistThenThrowException() {
        when(cannoliRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> cannoliService.getCannoli(1L));

        verify(cannoliRepository).findById(1L);
        verifyNoMoreInteractions(cannoliRepository);
    }

    @Test
    @DisplayName("getCannolis — alle producten")
    void getProductsShouldReturnsAllCannolis() {
        when(cannoliRepository.findAll()).thenReturn(List.of(
                c(1L, "a", "t", "d", "i", 1.0)
        ));

        List<Cannoli> cannolis = cannoliService.getCannolis();

        assertNotNull(cannolis);
        assertFalse(cannolis.isEmpty());
        assertEquals(1, cannolis.size());
        verify(cannoliRepository).findAll();
        verifyNoMoreInteractions(cannoliRepository);
    }

    @Test
    @DisplayName("findCannoliListByType — gevonden → lijst terug")
    void findCannoliListByTypeWhenCannoliTypeIsFoundThenReturnsAListOfCannolis() {
        String type = "glutenfree";
        when(cannoliRepository.findByCannoliTypeContainingIgnoreCase(type))
                .thenReturn(List.of(c(2L, "x", type, "d", "i", 2.0)));

        var result = cannoliService.findCannoliListByType(type);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(type, result.get(0).getCannoliType());
        verify(cannoliRepository).findByCannoliTypeContainingIgnoreCase(type);
        verifyNoMoreInteractions(cannoliRepository);
    }

    @Test
    @DisplayName("findCannoliListByType — niet gevonden → RecordNotFoundException")
    void findCannoliListByTypeWhenCannoliTypeIsNotFoundThenThrowsException() {
        String type = "glutenfree";
        when(cannoliRepository.findByCannoliTypeContainingIgnoreCase(type)).thenReturn(List.of());

        assertThrows(RecordNotFoundException.class, () -> cannoliService.findCannoliListByType(type));

        verify(cannoliRepository).findByCannoliTypeContainingIgnoreCase(type);
        verifyNoMoreInteractions(cannoliRepository);
    }

    @Test
    @DisplayName("deleteCannoli — bestaat → deleteById aangeroepen")
    void deleteProductWhenProductExists() {
        when(cannoliRepository.existsById(1L)).thenReturn(true);

        cannoliService.deleteCannoli(1L);

        verify(cannoliRepository).existsById(1L);
        verify(cannoliRepository).deleteById(1L);
        verifyNoMoreInteractions(cannoliRepository);
    }

    @Test
    @DisplayName("updateCannoli — bestaat → save aangeroepen met wijzigingen")
    void updateProductWhenProductExists() {
        var existing = c(1L, "test", "test", "test", "test", 1.0);
        when(cannoliRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(cannoliRepository.save(any(Cannoli.class))).thenAnswer(inv -> inv.getArgument(0));

        existing.setCannoliName("nieuw");
        Cannoli updated = cannoliService.updateCannoli(existing);

        assertThat(updated.getId()).isEqualTo(1L);
        assertThat(updated.getCannoliName()).isEqualTo("nieuw");
        verify(cannoliRepository).findById(1L);
        verify(cannoliRepository).save(existing);
        verifyNoMoreInteractions(cannoliRepository);
    }

    @Test
    @DisplayName("updateCannoli — bestaat niet → RecordNotFoundException")
    void updateCannoliWhenCannoliDoesNotExistThenThrowException() {
        var cannoli = c(1L, "test", "test", "test", "test", 1.0);
        when(cannoliRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> cannoliService.updateCannoli(cannoli));

        verify(cannoliRepository).findById(1L);
        verify(cannoliRepository, never()).save(any());
        verifyNoMoreInteractions(cannoliRepository);
    }

    @Test
    @DisplayName("createCannoli — geldig → save en entity terug")
    void createProductWhenProductIsValid() {
        var cannoli = c(1L, "test", "test", "test", "test", 1.0);
        when(cannoliRepository.save(cannoli)).thenReturn(cannoli);

        Cannoli result = cannoliService.createCannoli(cannoli);

        assertEquals(cannoli, result);
        verify(cannoliRepository).save(cannoli);
        verifyNoMoreInteractions(cannoliRepository);
    }
}