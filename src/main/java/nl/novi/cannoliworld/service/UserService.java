package nl.novi.cannoliworld.service;
import nl.novi.cannoliworld.dtos.UserDto;
import nl.novi.cannoliworld.models.User;
import java.util.Collection;
import java.util.Optional;

public interface UserService {
    Collection<User> getUsers();
    Optional<User> getUser(String username);
    String createUser(UserDto dto);
    void deleteUser(String username);
    boolean userExists(String username);
    void assignPersonToUser(Long personId, String username);
    void assignImageToUser(String username, String fileName);
}

