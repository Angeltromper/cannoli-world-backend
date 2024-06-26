package nl.novi.cannoliworld.repositories;

import nl.novi.cannoliworld.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    boolean existsById(String username);

    Optional<User> findById(String username);

    User save(User user);

    void deleteById(String username);
}
