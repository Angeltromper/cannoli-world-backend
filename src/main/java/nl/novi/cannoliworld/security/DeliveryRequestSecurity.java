package nl.novi.cannoliworld.security;

import lombok.RequiredArgsConstructor;
import nl.novi.cannoliworld.models.User;
import nl.novi.cannoliworld.repositories.DeliveryRequestRepository;
import nl.novi.cannoliworld.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("deliveryRequestSecurity")
@RequiredArgsConstructor
public class DeliveryRequestSecurity {

    private final UserRepository userRepository;                 // JpaRepository<User, String>
    private final DeliveryRequestRepository deliveryRequestRepository;

    public boolean isOwner(Long id, Authentication auth) {
        if (auth == null) return false;
        return userRepository.findById(auth.getName())
                .map(User::getPerson)
                .filter(Objects::nonNull)
                .map(p -> deliveryRequestRepository.existsByIdAndApplier_Id(id, p.getId()))
                .orElse(false);
    }
}
