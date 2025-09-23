package nl.novi.cannoliworld.repositories;

import nl.novi.cannoliworld.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    // Handig als je op naam wilt blijven werken:
    default Optional<User> findByUsername(String username) {
        return findById(username);
    }
    default boolean existsByUsername(String username) {
        return existsById(username);
    }
}
