package nl.novi.cannoliworld.security;
import nl.novi.cannoliworld.repositories.DeliveryRequestRepository;
import nl.novi.cannoliworld.repositories.UserRepository;
import nl.novi.cannoliworld.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component("deliveryRequestSecurity")
public class DeliveryRequestSecurity {

    private final UserRepository userRepository;
    private final DeliveryRequestRepository deliveryRequestRepository;

    public DeliveryRequestSecurity(UserRepository userRepository,
                                   DeliveryRequestRepository deliveryRequestRepository) {
        this.userRepository = userRepository;
        this.deliveryRequestRepository = deliveryRequestRepository;
    }

    public boolean isOwner(Long id, Authentication auth) {
        if (auth == null) return false;

        return userRepository.findByUsername(auth.getName())
                .map(User::getPerson)
                .filter(Objects::nonNull)
                .map(p -> deliveryRequestRepository.existsByIdAndApplier_Id(id, p.getId()))
                .orElse(false);
    }
}
