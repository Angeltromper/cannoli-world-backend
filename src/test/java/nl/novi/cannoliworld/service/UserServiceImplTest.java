package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.exeptions.RecordNotFoundException;
import nl.novi.cannoliworld.models.Person;
import nl.novi.cannoliworld.models.User;
import nl.novi.cannoliworld.repositories.FileUploadRepository;
import nl.novi.cannoliworld.repositories.PersonRepository;
import nl.novi.cannoliworld.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock private FileUploadRepository fileUploadRepository;
    @Mock private UserRepository userRepository;
    @Mock private PersonRepository personRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("assignPersonToUser — koppelt persoon aan user en slaat op")
    void assignPersonToUser_ok() {
        Person person = new Person();
        person.setId(1L);

        User user = new User();
        user.setUsername("angel");
        user.setPassword("password");

        when(userRepository.findById("angel")).thenReturn(Optional.of(user));
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        userService.assignPersonToUser(1L, "angel");

        assertEquals(person, user.getPerson());
        verify(userRepository).findById("angel");
        verify(personRepository).findById(1L);
        verify(userRepository).save(user);
        verifyNoMoreInteractions(userRepository, personRepository);
    }

    @Test
    @DisplayName("assignPersonToUser — user niet gevonden")
    void assignPersonToUser_userNotFound() {
        when(userRepository.findById("unknown")).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class,
                () -> userService.assignPersonToUser(1L, "unknown"));

        verify(userRepository).findById("unknown");
        verify(personRepository, never()).findById(any());
        verify(userRepository, never()).save(any());
        verifyNoMoreInteractions(userRepository, personRepository);
    }

    @Test
    @DisplayName("assignPersonToUser — person niet gevonden")
    void assignPersonToUser_personNotFound() {
        User user = new User();
        user.setUsername("angel");

        when(userRepository.findById("angel")).thenReturn(Optional.of(user));
        when(personRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class,
                () -> userService.assignPersonToUser(99L, "angel"));

        verify(userRepository).findById("angel");
        verify(personRepository).findById(99L);
        verify(userRepository, never()).save(any());
        verifyNoMoreInteractions(userRepository, personRepository);
    }
}
