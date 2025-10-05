package nl.novi.cannoliworld.repositories;

import nl.novi.cannoliworld.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    boolean existsByUsernameAndAuthority(String username, String authority);
}
