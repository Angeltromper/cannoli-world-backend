package nl.novi.cannoliworld.repositories;

import nl.novi.cannoliworld.models.Cannoli;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CannoliRepository extends JpaRepository<Cannoli, String> {

    Optional<Cannoli> findById(Long id);

    void deleteById(Long id);

    List<Cannoli> findByCannoliNameContainingIgnoreCase(String cannoliName);

    List<Cannoli> findByCannoliTypeContainingIgnoreCase(String cannoliType);
}
