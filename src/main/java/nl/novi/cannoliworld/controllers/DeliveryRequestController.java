package nl.novi.cannoliworld.controllers;

import nl.novi.cannoliworld.dtos.CreateDeliveryRequestDto;
import nl.novi.cannoliworld.dtos.DeliveryRequestDto;
import nl.novi.cannoliworld.dtos.DeliveryRequestStatusDto;
import nl.novi.cannoliworld.models.DeliveryRequest;
import nl.novi.cannoliworld.service.DeliveryRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/deliveryRequests")
public class DeliveryRequestController {

    private final DeliveryRequestService service;

    public DeliveryRequestController(DeliveryRequestService service) {
        this.service = service;
    }

    /** ADMIN: alle verzoeken */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DeliveryRequestDto>> getAll() {
        var dtos = service.getDeliveryRequests().stream()
                .map(DeliveryRequestDto::fromDeliveryRequest)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /** KLANT: eigen verzoeken (op basis van ingelogde gebruiker) */
    @GetMapping("/mine")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DeliveryRequestDto>> mine(Authentication auth) {
        String username = auth.getName(); // komt uit JwtRequestFilter -> UsernamePasswordAuthenticationToken
        var dtos = service.getMyDeliveryRequests(username).stream()
                .map(DeliveryRequestDto::fromDeliveryRequest)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /** DETAIL: alleen admin of eigenaar mag details zien */
    @GetMapping("/{id:\\d+}")
    @PreAuthorize("hasRole('ADMIN') or @deliveryRequestSecurity.isOwner(#id, authentication)")
    public ResponseEntity<DeliveryRequestDto> getOne(@PathVariable Long id) {
        DeliveryRequest dr = service.getDeliveryRequest(id);
        return ResponseEntity.ok(DeliveryRequestDto.fromDeliveryRequest(dr));
    }

    /** CREATE: klant en admin mogen aanmaken */
    @PostMapping({"","/create"})
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<DeliveryRequestDto> create(
            @Valid @RequestBody CreateDeliveryRequestDto dto,
            Authentication auth
    ) {
        String username = auth.getName(); // eigenaar vastleggen op basis van ingelogde user
        DeliveryRequest created = service.createDeliveryRequest(dto, username);

        return ResponseEntity
                .created(URI.create("/deliveryRequests/" + created.getId()))
                .body(DeliveryRequestDto.fromDeliveryRequest(created));
    }

    /** ADMIN: status/bewerking aanpassen */
    @PutMapping("/{id:\\d+}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody DeliveryRequestStatusDto statusDto
    ) {
        service.updateDeliveryRequest(id, statusDto);
        return ResponseEntity.ok().build();
    }

    /** ADMIN: verwijderen */
    @DeleteMapping("/{id:\\d+}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteDeliveryRequest(id);
        return ResponseEntity.noContent().build();
    }
}

