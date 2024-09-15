package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.models.User;

import java.util.*;

public interface UserService {

    List<User> getUsers();

    Collection<User> getUser();

    Optional<User> getUser(String username);

    String createUser(User user);

    void deleteUser(String username);

    boolean userExists(String username);

    void assignPersonToUser(Long id, String username);

    void assignPictureToUser(String fileName, String username);
}
