package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.dtos.CreateDeliveryRequestDto;
import nl.novi.cannoliworld.dtos.DeliveryRequestStatusDto;
import nl.novi.cannoliworld.exceptions.RecordNotFoundException;
import nl.novi.cannoliworld.models.*;
import nl.novi.cannoliworld.repositories.CannoliRepository;
import nl.novi.cannoliworld.repositories.DeliveryRequestRepository;
import nl.novi.cannoliworld.repositories.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryRequestServiceImplTest {

    @Mock private PersonRepository personRepository;
    @Mock private DeliveryRequestRepository deliveryRequestRepository;
    @Mock private CannoliRepository cannoliRepository;

    @InjectMocks
    private DeliveryRequestServiceImpl service;

    private static Cannoli cannoli(long id, String name, double price) {
        Cannoli c = new Cannoli();
        c.setId(id);
        c.setCannoliName(name);
        c.setPrice(price);
        return c;
    }
    private static DeliveryRequest entity(long id, DeliveryRequestStatus status) {
        DeliveryRequest dr = new DeliveryRequest();
        dr.setId(id);
        dr.setStatus(status);
        return dr;
    }
    private static CreateDeliveryRequestDto.ItemDto item(long cannoliId, int qty) {
        var it = new CreateDeliveryRequestDto.ItemDto();
        it.setCannoliId(cannoliId);
        it.setQuantity(qty);
        return it;
    }

    @Test
    @DisplayName("createDeliveryRequest — bouwt items (geaggregeerd), status NEW en slaat op")
    void create_ok() {
        var dto = new CreateDeliveryRequestDto();
        dto.setComment("please deliver");

        dto.setItems(List.of(item(1L, 2), item(2L, 1), item(1L, 3)));

        var person = new Person(); person.setId(11L);
        when(personRepository.findByUserUsername("angel")).thenReturn(Optional.of(person));
        when(cannoliRepository.findAllById(Set.of(1L, 2L)))
                .thenReturn(List.of(cannoli(1L, "Vanille", 2.5), cannoli(2L, "Pistache", 3.0)));

        when(deliveryRequestRepository.save(any(DeliveryRequest.class)))
                .thenAnswer(inv -> {
                    DeliveryRequest saved = inv.getArgument(0);
                    saved.setId(42L);
                    return saved;
                });

        DeliveryRequest saved = service.createDeliveryRequest(dto, "angel");

        assertThat(saved.getId()).isEqualTo(42L);
        assertThat(saved.getStatus()).isEqualTo(DeliveryRequestStatus.NEW);
        assertThat(saved.getApplier().getId()).isEqualTo(11L);
        assertThat(saved.getCannoliList()).hasSize(2);
        var mapById = new HashMap<Long, CannoliItem>();
        saved.getCannoliList().forEach(ci -> mapById.put(ci.getArtikelnummer(), ci));
        assertThat(mapById.get(1L).getQty()).isEqualTo(5);   // 2 + 3
        assertThat(mapById.get(1L).getNaam()).isEqualTo("Vanille");
        assertThat(mapById.get(2L).getQty()).isEqualTo(1);
        assertThat(mapById.get(2L).getPrijs()).isEqualTo(3.0);

        verify(personRepository).findByUserUsername("angel");
        verify(cannoliRepository).findAllById(Set.of(1L, 2L));
        verify(deliveryRequestRepository).save(any(DeliveryRequest.class));
        verifyNoMoreInteractions(personRepository, cannoliRepository, deliveryRequestRepository);
    }

    @Test
    @DisplayName("createDeliveryRequest — lege items → IllegalArgumentException")
    void create_emptyItems() {
        var dto = new CreateDeliveryRequestDto();
        dto.setItems(Collections.emptyList());
        assertThrows(IllegalArgumentException.class, () -> service.createDeliveryRequest(dto, "user"));
    }

    @Test
    @DisplayName("createDeliveryRequest — persoon niet gevonden")
    void create_personNotFound() {
        var dto = new CreateDeliveryRequestDto();
        dto.setItems(List.of(item(1L, 1)));
        when(personRepository.findByUserUsername("user")).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> service.createDeliveryRequest(dto, "user"));
    }

    @Test
    @DisplayName("createDeliveryRequest — onbekende cannoliId(s)")
    void create_missingCatalogItems() {
        var dto = new CreateDeliveryRequestDto();
        dto.setItems(List.of(item(1L, 1)));
        when(personRepository.findByUserUsername("user")).thenReturn(Optional.of(new Person()));
        when(cannoliRepository.findAllById(Set.of(1L))).thenReturn(List.of()); // niets terug
        assertThrows(RecordNotFoundException.class, () -> service.createDeliveryRequest(dto, "user"));
    }

    @Test
    @DisplayName("getDeliveryRequests — alles")
    void list_all() {
        when(deliveryRequestRepository.findAll()).thenReturn(List.of(new DeliveryRequest(), new DeliveryRequest()));
        var list = service.getDeliveryRequests();
        assertThat(list).hasSize(2);
        verify(deliveryRequestRepository).findAll();
    }

    @Test
    @DisplayName("getDeliveryRequest — gevonden")
    void get_found() {
        when(deliveryRequestRepository.findById(3L)).thenReturn(Optional.of(entity(3L, DeliveryRequestStatus.NEW)));
        var dr = service.getDeliveryRequest(3L);
        assertThat(dr.getId()).isEqualTo(3L);
        verify(deliveryRequestRepository).findById(3L);
    }

    @Test
    @DisplayName("getDeliveryRequest — niet gevonden")
    void get_notFound() {
        when(deliveryRequestRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> service.getDeliveryRequest(99L));
    }

    @Test
    @DisplayName("getMyDeliveryRequests — op username → zoekt person en requests")
    void myRequests() {
        var person = new Person(); person.setId(5L);
        when(personRepository.findByUserUsername("angel")).thenReturn(Optional.of(person));
        when(deliveryRequestRepository.findByApplier_Id(5L)).thenReturn(List.of(new DeliveryRequest()));

        var list = service.getMyDeliveryRequests("angel");

        assertThat(list).hasSize(1);
        verify(personRepository).findByUserUsername("angel");
        verify(deliveryRequestRepository).findByApplier_Id(5L);
    }

    @Test
    @DisplayName("updateDeliveryRequest — ok: NEW → AVAILABLE")
    void updateStatus_ok() {
        var dr = entity(7L, DeliveryRequestStatus.NEW);
        when(deliveryRequestRepository.findById(7L)).thenReturn(Optional.of(dr));
        when(deliveryRequestRepository.save(dr)).thenReturn(dr);

        var dto = new DeliveryRequestStatusDto();
        dto.setStatus(DeliveryRequestStatus.AVAILABLE);

        service.updateDeliveryRequest(7L, dto);

        assertThat(dr.getStatus()).isEqualTo(DeliveryRequestStatus.AVAILABLE);
        verify(deliveryRequestRepository).findById(7L);
        verify(deliveryRequestRepository).save(dr);
    }

    @Test
    @DisplayName("updateDeliveryRequest — zelfde status → geen save")
    void updateStatus_same_noop() {
        var dr = entity(7L, DeliveryRequestStatus.NEW);
        when(deliveryRequestRepository.findById(7L)).thenReturn(Optional.of(dr));

        var dto = new DeliveryRequestStatusDto();
        dto.setStatus(DeliveryRequestStatus.NEW);

        service.updateDeliveryRequest(7L, dto);

        verify(deliveryRequestRepository).findById(7L);
        verify(deliveryRequestRepository, never()).save(any());
    }

    @Test
    @DisplayName("updateDeliveryRequest — ongeldige overgang → IllegalStateException")
    void updateStatus_invalidTransition() {
        var dr = entity(7L, DeliveryRequestStatus.NEW); // vanuit NEW mag alleen AVAILABLE
        when(deliveryRequestRepository.findById(7L)).thenReturn(Optional.of(dr));

        var dto = new DeliveryRequestStatusDto();
        dto.setStatus(DeliveryRequestStatus.FINISHED);

        assertThrows(IllegalStateException.class, () -> service.updateDeliveryRequest(7L, dto));
        verify(deliveryRequestRepository).findById(7L);
        verify(deliveryRequestRepository, never()).save(any());
    }

    @Test
    @DisplayName("updateDeliveryRequest — null status → IllegalArgumentException")
    void updateStatus_null() {
        var dr = entity(7L, DeliveryRequestStatus.NEW);
        when(deliveryRequestRepository.findById(7L)).thenReturn(Optional.of(dr));

        var dto = new DeliveryRequestStatusDto(); // status blijft null

        assertThrows(IllegalArgumentException.class, () -> service.updateDeliveryRequest(7L, dto));
        verify(deliveryRequestRepository).findById(7L);
        verify(deliveryRequestRepository, never()).save(any());
    }

    @Test
    @DisplayName("updateDeliveryRequest — id niet gevonden")
    void updateStatus_notFound() {
        when(deliveryRequestRepository.findById(8L)).thenReturn(Optional.empty());
        var dto = new DeliveryRequestStatusDto(); dto.setStatus(DeliveryRequestStatus.AVAILABLE);
        assertThrows(RecordNotFoundException.class, () -> service.updateDeliveryRequest(8L, dto));
    }

    @Test
    @DisplayName("deleteDeliveryRequest — alleen FINISHED mag worden verwijderd")
    void delete_finished_only() {
        var dr = entity(5L, DeliveryRequestStatus.FINISHED);
        when(deliveryRequestRepository.findById(5L)).thenReturn(Optional.of(dr));

        service.deleteDeliveryRequest(5L);

        verify(deliveryRequestRepository).findById(5L);
        verify(deliveryRequestRepository).delete(dr);
    }

    @Test
    @DisplayName("deleteDeliveryRequest — status niet FINISHED → IllegalStateException")
    void delete_notFinished_throws() {
        var dr = entity(6L, DeliveryRequestStatus.CONFIRMED);
        when(deliveryRequestRepository.findById(6L)).thenReturn(Optional.of(dr));

        assertThrows(IllegalStateException.class, () -> service.deleteDeliveryRequest(6L));
        verify(deliveryRequestRepository).findById(6L);
        verify(deliveryRequestRepository, never()).delete(any());
    }

    @Test
    @DisplayName("deleteDeliveryRequest — id niet gevonden")
    void delete_notFound() {
        when(deliveryRequestRepository.findById(9L)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> service.deleteDeliveryRequest(9L));
    }
}
