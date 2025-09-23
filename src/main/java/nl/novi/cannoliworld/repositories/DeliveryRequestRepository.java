package nl.novi.cannoliworld.repositories;
import nl.novi.cannoliworld.models.DeliveryRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DeliveryRequestRepository extends JpaRepository<DeliveryRequest, Long> {
    boolean existsByIdAndApplier_Id(Long id, Long perosnId);
    @EntityGraph(attributePaths = "applier")
    List<DeliveryRequest> findAll();
    @EntityGraph(attributePaths = "applier")
    Optional<DeliveryRequest> findById(Long id);
    List<DeliveryRequest> findByApplier_Id(Long applierId);
}