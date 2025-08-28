
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

@CrossOrigin // (overweeg origin + allowCredentials netjes te configureren)
@RestController
@RequestMapping("/deliveryRequests")
public class DeliveryRequestController {

    private final DeliveryRequestService service;

    public DeliveryRequestController(DeliveryRequestService service) {
        this.service = service;
    }

    // ADMIN
    @GetMapping({"", "/all"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DeliveryRequestDto>> getAll() {
        var dtos = service.getDeliveryRequests().stream()
                .map(DeliveryRequestDto::fromDeliveryRequest)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // MINE (ingelogde klant)
    @GetMapping("/mine")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DeliveryRequestDto>> mine(Authentication auth) {
        var dtos = service.getMyDeliveryRequests(auth.getName()).stream()
                .map(DeliveryRequestDto::fromDeliveryRequest)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // DETAIL (admin of eigenaar)
    @GetMapping("/{id:\\d+}")
    @PreAuthorize("hasRole('ADMIN') or @deliveryRequestSecurity.isOwner(#id, authentication)")
    public ResponseEntity<DeliveryRequestDto> getOne(@PathVariable Long id) {
        DeliveryRequest dr = service.getDeliveryRequest(id);
        return ResponseEntity.ok(DeliveryRequestDto.fromDeliveryRequest(dr));
    }

    // CREATE  (gebruik hier CreateDeliveryRequestDto)
    @PostMapping({"", "/create"})
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public ResponseEntity<DeliveryRequestDto> create(
            @Valid @RequestBody CreateDeliveryRequestDto dto,
            Authentication auth
    ) {
        // Als je applierId uit de token wil halen i.p.v. uit body:
        // Long applierId = userService.findIdByUsername(auth.getName());
        // DeliveryRequest created = service.createDeliveryRequest(dto, applierId);

        var created = service.createDeliveryRequest(dto, auth.getName());

        return ResponseEntity
        .created(URI.create("/deliveryRequests/" + created.getId()))
                .body(DeliveryRequestDto.fromDeliveryRequest(created));
    }

    // UPDATE STATUS (ADMIN)
    @PutMapping("/{id:\\d+}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody DeliveryRequestStatusDto statusDto
    ) {
        service.updateDeliveryRequest(id, statusDto);
        return ResponseEntity.ok().build();
    }

    // DELETE (ADMIN)
    @DeleteMapping({"/{id:\\d+}", "/delete/{id:\\d+}"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteDeliveryRequest(id);
        return ResponseEntity.noContent().build();
    }
}

