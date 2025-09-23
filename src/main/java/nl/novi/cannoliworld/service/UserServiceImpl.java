package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.exeptions.RecordNotFoundException;
import nl.novi.cannoliworld.exeptions.UsernameNotFoundException;
import nl.novi.cannoliworld.models.Person;
import nl.novi.cannoliworld.models.User;
import nl.novi.cannoliworld.repositories.PersonRepository;
import nl.novi.cannoliworld.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PersonRepository personRepository;

    public UserServiceImpl(UserRepository userRepository, PersonRepository personRepository) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
    }

    @Override
    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUser(String username) {
        return userRepository.findById(username);
    }

    @Override
    public String createUser(User user) {
        return userRepository.save(user).getUsername();
    }

    @Override
    public void deleteUser(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(username)));
        userRepository.delete(user);
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void assignPersonToUser(Long personId, String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(username)));
        var person = personRepository.findById(personId)
                .orElseThrow(() -> new RecordNotFoundException("Person %d not found".formatted(personId)));
        user.setPerson(person);
        userRepository.save(user);
    }

    @Override
    public void assignPersonToUser(String username, Long personId) {
        assignPersonToUser(personId, username);
    }

    @Override
    public void assignImageToUser(String fileName, String username) {
        // (nog niet geïmplementeerd in deze applicatie)
    }
}
