package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.dtos.DeliveryRequestInputDto;
import nl.novi.cannoliworld.dtos.DeliveryRequestStatusDto;
import nl.novi.cannoliworld.exeptions.RecordNotFoundException;
import nl.novi.cannoliworld.models.Cannoli;
import nl.novi.cannoliworld.models.DeliveryRequest;
import nl.novi.cannoliworld.repositories.CannoliRepository;
import nl.novi.cannoliworld.repositories.DeliveryRequestRepository;
import nl.novi.cannoliworld.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@Service

public class DeliveryRequestServiceImpl implements DeliveryRequestService {

    private final PersonRepository personRepository;
    private final DeliveryRequestRepository deliveryRequestRepository;
    private final CannoliRepository cannoliRepository;

    @Autowired
    public DeliveryRequestServiceImpl(DeliveryRequestRepository deliveryRequestRepository,
                                      PersonRepository personRepository, CannoliRepository cannoliRepository) {
        this.deliveryRequestRepository = deliveryRequestRepository;
        this.personRepository = personRepository;
        this.cannoliRepository = cannoliRepository;
    }

    @Override
    public List<DeliveryRequest> getDeliveryRequests() { return deliveryRequestRepository.findAll(); }

    @Override
    public DeliveryRequest getDeliveryRequest(Long id) {
        Optional<DeliveryRequest> deliveryRequest = deliveryRequestRepository.findById(id);

        if (deliveryRequest.isPresent()) {
            return deliveryRequest.get();
        } else {
            throw new RecordNotFoundException("deliveryRequest niet gevonden");

        }
    }

    @Override
    public DeliveryRequest createDeliveryRequest(DeliveryRequestInputDto deliveryRequestInputDto) {
        DeliveryRequest deliveryRequest = new DeliveryRequest();

        Map<Long, String> cannoliList2 = new HashMap<>();
        List<Long> cannoliListLong = deliveryRequestInputDto.getCannoliList();

        for (Long cannoli : cannoliListLong) {
            Optional<Cannoli> optional = cannoliRepository.findById(cannoli);

            if (!cannoliList2.containsKey(cannoli)) {
                cannoliList2.put(cannoli,"1-" + "x " + optional.get().cannoliName + "-" + '_' + '€' + optional.get().getPrice());
            } else {
                String[] customArr = cannoliList2.get(cannoli).split("-");

                int quantity = Integer. parseInt(customArr[0]);
                int actualQuantity = quantity + 1;

                double doubleValue = optional.get().getPrice() * actualQuantity;
                BigDecimal bigDecimalDouble = new BigDecimal(doubleValue);

                BigDecimal bigDecimalWithScale = bigDecimalDouble.setScale(2, RoundingMode.HALF_UP);

                cannoliList2.put(cannoli, actualQuantity + "-x" + optional.get().getCannoliName() + "-" + '_' + '€' + bigDecimalWithScale);
            }
        }
        deliveryRequest.setStatus(deliveryRequestInputDto.getStatus().AVAILABLE);
//        deliveryRequest.setCannoliList(cannoliList2);
        deliveryRequest.setComment(deliveryRequestInputDto.getComment());
        deliveryRequest.setApplier(personRepository.getReferenceById(deliveryRequestInputDto.getApplier()));
        return deliveryRequestRepository.save(deliveryRequest);
    }

    @Override
    public void updateDeliveryRequest(DeliveryRequestStatusDto deliveryRequestStatusDto) {
        Optional<DeliveryRequest> optionalDeliveryRequest = deliveryRequestRepository.findById(deliveryRequestStatusDto.getId());
        if(optionalDeliveryRequest.isPresent()){
            optionalDeliveryRequest.get().setStatus(deliveryRequestStatusDto.getStatus());
        } else {
            throw new RecordNotFoundException("Delivery request not found");
        }
    }

    @Override
    public void deleteDeliveryRequest(Long id) { deliveryRequestRepository.deleteById(id); }
}