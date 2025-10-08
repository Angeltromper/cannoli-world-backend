package nl.novi.cannoliworld.repositories;
import nl.novi.cannoliworld.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByPersonFirstnameContainingIgnoreCase(String personFirstname);

    List<Person> findByPersonLastnameContainingIgnoreCase(String personLastname);

    Optional<Person> findByUserUsername(String username);
}