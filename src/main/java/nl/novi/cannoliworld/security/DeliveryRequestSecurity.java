
package nl.novi.cannoliworld.security;

import nl.novi.cannoliworld.repositories.DeliveryRequestRepository;
import nl.novi.cannoliworld.repositories.PersonRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DeliveryRequestSecurity {

    private final DeliveryRequestRepository deliveryRequests;
    private final PersonRepository persons;

    public DeliveryRequestSecurity(DeliveryRequestRepository deliveryRequests,
                                   PersonRepository persons) {
        this.deliveryRequests = deliveryRequests;
        this.persons = persons;
    }


    public boolean isOwner(Long deliveryRequestId, Authentication auth) {
        if (auth == null || auth.getName() == null) return false;

        // 1) Vind de Person die bij de ingelogde username hoort
        var meOpt = persons.findByUserUsername(auth.getName());
        if (meOpt.isEmpty()) return false;
        var myPersonId = meOpt.get().getId();

        // 2) Vergelijk met applier.id van de bestelling
        return deliveryRequests.findById(deliveryRequestId)
                .map(dr -> dr.getApplier() != null
                        && Objects.equals(dr.getApplier().getId(), myPersonId))
                .orElse(false);
    }
}
