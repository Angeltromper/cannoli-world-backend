package nl.novi.cannoliworld.repositories;

import nl.novi.cannoliworld.models.DeliveryRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DeliveryRequestRepository extends JpaRepository<DeliveryRequest, Long> {

    Optional<DeliveryRequest> findById(Long id);

}