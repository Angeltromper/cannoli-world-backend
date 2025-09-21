package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.exeptions.RecordNotFoundException;
import nl.novi.cannoliworld.exeptions.UsernameNotFoundException;
import nl.novi.cannoliworld.models.Person;
import nl.novi.cannoliworld.models.User;
import nl.novi.cannoliworld.repositories.PersonRepository;
import nl.novi.cannoliworld.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock UserRepository userRepository;
    @Mock PersonRepository personRepository;

    @InjectMocks UserServiceImpl service;

    @Test
    void assignPersonToUser_userNotFound() {
        when(userRepository.findById("hans")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> service.assignPersonToUser("hans", 10L));

        verify(userRepository).findById("hans");
        verify(personRepository, never()).findById(anyLong());
        verify(userRepository, never()).save(any());
    }

    @Test
    void assignPersonToUser_personNotFound() {
        User u = new User();
        u.setUsername("hans");
        when(userRepository.findById("hans")).thenReturn(Optional.of(u));
        when(personRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class,
                () -> service.assignPersonToUser("hans", 99L));

        verify(userRepository).findById("hans");
        verify(personRepository).findById(99L);
        verify(userRepository, never()).save(any());
    }

    @Test
    void assignPersonToUser_ok() {
        User u = new User();
        u.setUsername("hans");
        Person p = new Person();
        p.setId(5L);

        when(userRepository.findById("hans")).thenReturn(Optional.of(u));
        when(personRepository.findById(5L)).thenReturn(Optional.of(p));

        service.assignPersonToUser("hans", 5L);

        assertThat(u.getPerson()).isSameAs(p);
        verify(userRepository).save(u);
    }
}
