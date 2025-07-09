package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.exeptions.RecordNotFoundException;
import nl.novi.cannoliworld.exeptions.UsernameAlreadyExistException;
import nl.novi.cannoliworld.models.Authority;
import nl.novi.cannoliworld.models.Person;
import nl.novi.cannoliworld.models.User;
import nl.novi.cannoliworld.repositories.FileUploadRepository;
import nl.novi.cannoliworld.repositories.PersonRepository;
import nl.novi.cannoliworld.repositories.UserRepository;
import nl.novi.cannoliworld.utils.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileUploadRepository fileUploadRepository;

    @Autowired
    private PersonServiceImpl personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUser(String username) {
        return userRepository.findById(username);
    }

    public boolean userExists(String username) { return userRepository.existsById(username); }

    public String createUser(User user) {

        if (userExists(user.getUsername())) {
            throw new UsernameAlreadyExistException("Username is al in gebruik!");
        }

        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        user.setEmail(user.getEmail());
        user.setApikey(randomString);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().clear();
        user.addAuthority(new Authority(user.getUsername(), "ROLE_USER"));
        user.setId((long) ((getUsers().size()) + 1));

        user.setPerson(personService.savePerson(new Person()));

        User newUser = userRepository.save(user);

        return newUser.getUsername();
    }


    @Override
    public void deleteUser(String username) { userRepository.deleteById(username); }

    public void assignPersonToUser(Long personId, String username) {
        var optionalUser = userRepository.findById(username);
        var optionalPerson = personRepository.findById(personId);

        if (optionalPerson.isPresent() && optionalUser.isPresent()) {
            var user = optionalUser.get();
            var person = optionalPerson.get();
            user.setPerson(person);
            userRepository.save(user);
        }
    }


    public void assignImageToUser(String fileName, String username) {

        var optionalUser = userRepository.findById(username);
        var optionalImage = fileUploadRepository.findByFileName(fileName);

        if (optionalImage.isPresent() && optionalUser.isPresent()) {
            var user = optionalUser.get();
            var image = optionalImage.get();

            user.setImage(image);
            userRepository.save(user);

        } else {
            throw new RecordNotFoundException("een van de twee is niet gevonden");
        }
    }
}
