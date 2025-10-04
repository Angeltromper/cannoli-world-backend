package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.exceptions.RecordNotFoundException;
import nl.novi.cannoliworld.repositories.CannoliRepository;
import nl.novi.cannoliworld.repositories.FileUploadRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CannoliServiceImplTest {

    @Mock CannoliRepository cannoliRepository;
    @Mock FileUploadRepository fileUploadRepository;

    @InjectMocks CannoliServiceImpl service;

    @Test
    void deleteProductWhenProductExists() {
        Long id = 1L;
        when(cannoliRepository.existsById(id)).thenReturn(true);

        service.deleteCannoli(id);

        verify(cannoliRepository).existsById(id);
        verify(cannoliRepository).deleteById(id);
    }

    @Test
    void deleteProductWhenProductNotExists_throwsNotFound() {
        Long id = 99L;
        when(cannoliRepository.existsById(id)).thenReturn(false);

        assertThrows(RecordNotFoundException.class, () -> service.deleteCannoli(id));
        verify(cannoliRepository, never()).deleteById(anyLong());
    }
}
