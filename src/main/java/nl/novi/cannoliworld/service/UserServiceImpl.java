package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.dtos.UserDto;
import nl.novi.cannoliworld.exeptions.UsernameNotFoundException;
import nl.novi.cannoliworld.models.Authority;
import nl.novi.cannoliworld.models.User;
import nl.novi.cannoliworld.repositories.UserRepository;
import nl.novi.cannoliworld.utils.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl {

    @Autowired
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

    public UserDto getUser(String username) {
        UserDto dto = new UserDto();
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            dto = fromUser(user.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
        return dto;
    }
    public boolean userExists(String username) { return userRepository.existsById(username); }

    public String createUser(UserDto userDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric( 20);
        userDto.setAuthorities(null);
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        User newUser = userRepository.save(toUser(userDto));
        return newUser.getUsername();
    }

    public void deleteUser(String username) { userRepository.deleteById(username); }

    public void updateUser(String username, UserDto newUser) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.setPassword(newUser.getPassword());
        user.setEmailAdress(newUser.getEmailAdress());
        userRepository.save(user);
    }

    public Set<Authority> getAuthorities (String username) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        UserDto userDto = fromUser(user);
        return userDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        User.addAuthority(new Authority(username,authority));
        userRepository.save(user);
    }
     public void removeAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthortiy.equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
     }

     public static UserDto fromUser(User user) {
        var dto = new UserDto();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.emailAdress = user.getEmailAdress();
        dto.authorities = user.getAuthorities();

        return dto;
     }

     public User toUser(UserDto userDto) {
        var user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmailAdress(userDto.getEmailAdress());

        if (userDto.getAuthorities() == null || userDto.getAuthorities().size() == 0) {
            Authority authority = new Authority(user.getUsername(), "ROLE_USER");
            user.addAuthority(authority);
        } else {
            for (Authority authority : userDto.getAuthorities()) {
                user.addAuthority(authority);
            }
        }

        return user;
     }

    public void assignPersonToUser(Long personId, String username) {
    }
}
