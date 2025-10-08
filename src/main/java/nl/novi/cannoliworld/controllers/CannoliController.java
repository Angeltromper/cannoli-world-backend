package nl.novi.cannoliworld.controllers;

import lombok.RequiredArgsConstructor;
import nl.novi.cannoliworld.dtos.CannoliDto;
import nl.novi.cannoliworld.dtos.CannoliInputDto;
import nl.novi.cannoliworld.models.Cannoli;
import nl.novi.cannoliworld.service.CannoliService;
import nl.novi.cannoliworld.service.PhotoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/cannolis")
@RequiredArgsConstructor
public class CannoliController {

    private final CannoliService cannoliService;
    private final PhotoService photoService;

    @GetMapping
    public List<CannoliDto> getCannolis(
            @RequestParam(value = "cannoliName", required = false, defaultValue = "") String cannoliName,
            @RequestParam(value = "cannoliType", required = false, defaultValue = "") String cannoliType) {

        List<Cannoli> cannoliList;
        boolean hasName = cannoliName != null && !cannoliName.isBlank();
        boolean hasType = cannoliType != null && !cannoliType.isBlank();

        if (!hasName && !hasType) {
            cannoliList = cannoliService.getCannolis();
        } else if (hasName && !hasType) {
            cannoliList = cannoliService.findCannoliListByName(cannoliName);
        } else {
            cannoliList = cannoliService.findCannoliListByType(cannoliType);
        }
        return cannoliList.stream().map(CannoliDto::fromCannoli).toList();
    }

    @GetMapping("/{id}")
    public CannoliDto getCannoli(@PathVariable("id") Long id) {
        return CannoliDto.fromCannoli(cannoliService.getCannoli(id));
    }

    @PostMapping
    public ResponseEntity<CannoliDto> createCannoli(@Valid @RequestBody CannoliInputDto dto) {
        var saved = cannoliService.createCannoli(dto.toCannoli());
        return ResponseEntity.created(URI.create("/cannolis/" + saved.getId()))
                .body(CannoliDto.fromCannoli(saved));
    }

    @PutMapping("/{id}")
    public CannoliDto updateCannoli(@PathVariable Long id, @Valid @RequestBody Cannoli updated) {
        return CannoliDto.fromCannoli(cannoliService.updateCannoli(id, updated));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCannoli(@PathVariable("id") Long id) {
        cannoliService.deleteCannoli(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/image/{filename:.+}")
    public ResponseEntity<Void> assignImageToCannoli(@PathVariable("id") Long cannoliId,
                                                     @PathVariable("filename") String fileName) {
        cannoliService.assignImageToCannoli(fileName, cannoliId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadImageToCannoli(@PathVariable("id") Long cannoliId,
                                                     @RequestParam("file") MultipartFile file) {
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/download/")
                .pathSegment(Objects.requireNonNull(file.getOriginalFilename()))
                .toUriString();

        String stored = photoService.storeFile(file, url);
        cannoliService.assignImageToCannoli(stored, cannoliId);
        return ResponseEntity.noContent().build();
    }
}
