package nl.novi.cannoliworld.security;

import lombok.RequiredArgsConstructor;
import nl.novi.cannoliworld.repositories.DeliveryRequestRepository;
import nl.novi.cannoliworld.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("deliveryRequestSecurity")
@RequiredArgsConstructor
public class DeliveryRequestSecurity {

    private final UserRepository userRepository;
    private final DeliveryRequestRepository deliveryRequestRepository;

    public boolean isOwner(Long id, Authentication authentication) {
        if (authentication == null) return false;
        String username = authentication.getName();

        return userRepository.findById(username) // of findByUsername(username)
                .map(user -> user.getPerson())   // user -> Person
                .filter(Objects::nonNull)        // defensief
                .map(person -> deliveryRequestRepository
                        .existsByIdAndApplier_Id(id, person.getId()))
                .orElse(false);
    }
}
