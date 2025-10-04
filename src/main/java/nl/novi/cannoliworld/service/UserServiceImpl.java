package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.dtos.UserDto;
import nl.novi.cannoliworld.exceptions.RecordNotFoundException;
import nl.novi.cannoliworld.exceptions.UsernameNotFoundException;
import nl.novi.cannoliworld.models.User;
import nl.novi.cannoliworld.repositories.PersonRepository;
import nl.novi.cannoliworld.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository,
                           PersonRepository personRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String createUser(UserDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalStateException("Username bestaat al");
        }
        if (userRepository.existsByEmail(dto.getEmailAddress())) {
            throw new IllegalStateException("Email bestaat al");
        }

        User u = new User();
        u.setUsername(dto.getUsername());
        u.setEmail(dto.getEmailAddress());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));

        return userRepository.save(u).getUsername();
    }

    @Override
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(username)));
        userRepository.delete(user);
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void assignPersonToUser(Long personId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(username)));
        var person = personRepository.findById(personId)
                .orElseThrow(() -> new RecordNotFoundException("Person %d not found".formatted(personId)));
        user.setPerson(person);
        userRepository.save(user);
    }

    @Override
    public void assignImageToUser(String username, String fileName) {
        // TODO: implementeren als je FileUploadResponse koppelt
    }
}
