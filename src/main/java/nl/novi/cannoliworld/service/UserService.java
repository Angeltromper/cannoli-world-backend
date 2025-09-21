package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.models.User;

import java.util.*;

public interface UserService {

    Collection<User> getUsers();

    Optional<User> getUser(String username);

    String createUser(User user);

    void deleteUser(String username);

    boolean userExists(String username);

    void assignPersonToUser(Long id, String username);

    void assignImageToUser(String fileName, String username);

    void assignPersonToUser(String username, Long personId);
}
