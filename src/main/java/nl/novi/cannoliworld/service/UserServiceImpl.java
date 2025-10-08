package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.dtos.UserDto;
import nl.novi.cannoliworld.exceptions.RecordNotFoundException;
import nl.novi.cannoliworld.exceptions.UsernameNotFoundException;
import nl.novi.cannoliworld.models.Authority;
import nl.novi.cannoliworld.models.Person;
import nl.novi.cannoliworld.models.User;
import nl.novi.cannoliworld.repositories.AuthorityRepository;
import nl.novi.cannoliworld.repositories.PersonRepository;
import nl.novi.cannoliworld.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
//@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PersonRepository personRepository,
                           AuthorityRepository authorityRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public String createUser(UserDto dto) {
       String email = dto.getEmailAddress().trim().toLowerCase();

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalStateException("Username bestaat al");
        }
        if (userRepository.existsByEmail(dto.getEmailAddress())) {
            throw new IllegalStateException("Email bestaat al");
        }

        User u = new User();
        u.setUsername(dto.getUsername());
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        u.setEnabled(true);

        Person p = personRepository.save(new Person());
        u.setPerson(p);

        User saved = userRepository.save(u);

        authorityRepository.save(new Authority(saved.getUsername(), "ROLE_USER"));

    return saved.getUsername();
    }


    @Override
    @Transactional
    public void deleteUser(String usernameToDelete) {
        User user = userRepository.findByUsername(usernameToDelete)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user.getPerson() != null) {
            user.setPerson(null);
            userRepository.save(user);
        }

        userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional
    public void assignPersonToUser(Long personId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(username)));
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new RecordNotFoundException("Person %d not found".formatted(personId)));
        user.setPerson(person);
        userRepository.save(user);
    }

    @Override
    public void assignImageToUser(String username, String fileName) {

    }
}
