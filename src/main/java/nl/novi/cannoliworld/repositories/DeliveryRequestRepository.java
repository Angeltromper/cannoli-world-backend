package nl.novi.cannoliworld.repositories;

import nl.novi.cannoliworld.models.DeliveryRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DeliveryRequestRepository extends JpaRepository<DeliveryRequest, Long> {

    @NotNull Optional<DeliveryRequest> findById(@NotNull Long id);

}
