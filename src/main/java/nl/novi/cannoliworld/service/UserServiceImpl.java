package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.dtos.UserDto;
import nl.novi.cannoliworld.exeptions.RecordNotFoundException;
import nl.novi.cannoliworld.exeptions.UserNameAlreadyExistException;
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
import java.util.List;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PhotoService photoService;


    @Override
    public List<User> getUsers() { return userRepository.findAll(); }


    @Override
    public Collection<User> getUser() {
        return null;
    }

    @Override
    public Optional<User> getUser(String username) {
        return userRepository.findById(username);
    }

    public String createUser(User user) {
        return null;
    }

    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

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


    public void assignPictureToUser(String fileName, String username) {
        var optionalUser = userRepository.findById(username);
        var optionalPicture = fileUploadRepository.findByFileName(fileName);

        if (optionalPicture.isPresent() && optionalUser.isPresent()) {
            var user = optionalUser.get();
            var picture = optionalPicture.get();

            user.setPicture(picture);
            userRepository.save(user);

        } else {
            throw new RecordNotFoundException("een van de twee is niet gevonden");
        }
    }
}