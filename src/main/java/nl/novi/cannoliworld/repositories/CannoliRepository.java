
package nl.novi.cannoliworld.repositories;

import nl.novi.cannoliworld.models.Cannoli;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CannoliRepository extends JpaRepository<Cannoli, Long> {

    List<Cannoli> findByCannoliNameContainingIgnoreCase(String cannoliName);

    List<Cannoli> findByCannoliTypeContainingIgnoreCase(String cannoliType);
}


