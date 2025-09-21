package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.dtos.CreateDeliveryRequestDto;
import nl.novi.cannoliworld.dtos.DeliveryRequestStatusDto;
import nl.novi.cannoliworld.exeptions.RecordNotFoundException;
import nl.novi.cannoliworld.models.Cannoli;
import nl.novi.cannoliworld.models.CannoliItem;
import nl.novi.cannoliworld.models.DeliveryRequest;
import nl.novi.cannoliworld.models.DeliveryRequestStatus;
import nl.novi.cannoliworld.models.Person;
import nl.novi.cannoliworld.repositories.CannoliRepository;
import nl.novi.cannoliworld.repositories.DeliveryRequestRepository;
import nl.novi.cannoliworld.repositories.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeliveryRequestServiceImpl implements DeliveryRequestService {

    private final PersonRepository personRepository;
    private final DeliveryRequestRepository deliveryRequestRepository;
    private final CannoliRepository cannoliRepository;

    public DeliveryRequestServiceImpl(DeliveryRequestRepository deliveryRequestRepository,
                                      PersonRepository personRepository,
                                      CannoliRepository cannoliRepository) {
        this.deliveryRequestRepository = deliveryRequestRepository;
        this.personRepository = personRepository;
        this.cannoliRepository = cannoliRepository;
    }

    /** Toegestane status-overgangen */
    private static final Map<DeliveryRequestStatus, Set<DeliveryRequestStatus>> ALLOWED = Map.of(
            DeliveryRequestStatus.NEW,       Set.of(DeliveryRequestStatus.AVAILABLE),
            DeliveryRequestStatus.AVAILABLE, Set.of(DeliveryRequestStatus.CONFIRMED),
            DeliveryRequestStatus.CONFIRMED, Set.of(DeliveryRequestStatus.FINISHED),
            DeliveryRequestStatus.FINISHED,  Set.of()
    );

    private static boolean isAllowed(DeliveryRequestStatus from, DeliveryRequestStatus to) {
        return ALLOWED.getOrDefault(from, Set.of()).contains(to);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryRequest> getDeliveryRequests() {
        return deliveryRequestRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public DeliveryRequest getDeliveryRequest(Long id) {
        return deliveryRequestRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("DeliveryRequest niet gevonden: id=" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryRequest> getMyDeliveryRequests(String username) {
        Person person = personRepository.findByUserUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("Persoon niet gevonden voor gebruiker " + username));
        return deliveryRequestRepository.findByApplier_Id(person.getId());
    }

    @Override
    public DeliveryRequest createDeliveryRequest(CreateDeliveryRequestDto dto, String username) {
        if (dto == null || dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new IllegalArgumentException("Request body/items mogen niet leeg zijn");
        }

        // items aggregeren per cannoliId (quantity optellen)
        Map<Long, Integer> qtyById = dto.getItems().stream()
                .collect(Collectors.toMap(
                        CreateDeliveryRequestDto.ItemDto::getCannoliId,
                        CreateDeliveryRequestDto.ItemDto::getQuantity,
                        Integer::sum
                ));

        // validatie
        qtyById.forEach((id, qty) -> {
            if (id == null) throw new IllegalArgumentException("cannoliId mag niet null zijn");
            if (qty == null || qty <= 0) throw new IllegalArgumentException("quantity moet > 0 zijn voor cannoliId=" + id);
        });

        // applier ophalen
        Person applier = personRepository.findByUserUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("Persoon niet gevonden voor user " + username));

        // catalogusproducten laden
        Set<Long> ids = qtyById.keySet();
        Map<Long, Cannoli> cannoliById = cannoliRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(Cannoli::getId, c -> c));

        if (cannoliById.size() != ids.size()) {
            Set<Long> missing = new HashSet<>(ids);
            missing.removeAll(cannoliById.keySet());
            throw new RecordNotFoundException("Cannoli niet gevonden voor id(s): " + missing);
        }

        // JSONB-snapshot bouwen (serverprijzen!)
        List<CannoliItem> items = qtyById.entrySet().stream()
                .map(e -> {
                    Cannoli c = cannoliById.get(e.getKey());
                    CannoliItem ci = new CannoliItem();
                    ci.setArtikelnummer(c.getId());
                    ci.setNaam(c.getCannoliName());
                    ci.setPrijs(c.getPrice());   // double uit catalogus
                    ci.setQty(e.getValue());
                    return ci;
                })
                .collect(Collectors.toList());

        // entity vullen
        DeliveryRequest entity = new DeliveryRequest();
        entity.setApplier(applier);
        entity.setComment(dto.getComment());
        entity.setStatus(DeliveryRequestStatus.NEW);
        entity.setCannoliList(items);

        // opslaan
        return deliveryRequestRepository.save(entity);
    }

    @Override
    public void updateDeliveryRequest(Long id, DeliveryRequestStatusDto statusDto) {
        DeliveryRequest entity = deliveryRequestRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("DeliveryRequest niet gevonden: id=" + id));

        if (statusDto == null || statusDto.getStatus() == null) {
            throw new IllegalArgumentException("Status mag niet leeg zijn");
        }

        DeliveryRequestStatus current = entity.getStatus();
        DeliveryRequestStatus next    = statusDto.getStatus();

        if (current == next) {
            // niets te doen
            return;
        }

        // Valideer status-overgang
        if (!isAllowed(current, next)) {
            throw new IllegalStateException("Ongeldige status-overgang: " + current + " -> " + next);
        }

        entity.setStatus(next);
        deliveryRequestRepository.save(entity);
    }

    @Override
    public void deleteDeliveryRequest(Long id) {
        DeliveryRequest entity = deliveryRequestRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("DeliveryRequest niet gevonden: id=" + id));

        if (entity.getStatus() != DeliveryRequestStatus.FINISHED) {
            throw new IllegalStateException("Alleen FINISHED bestellingen mogen worden verwijderd.");
        }

        deliveryRequestRepository.delete(entity);
    }
}
