package nl.novi.cannoliworld.controllers;

import javax.validation.Valid;
import nl.novi.cannoliworld.dtos.CannoliDto;
import nl.novi.cannoliworld.dtos.CannoliInputDto;
import nl.novi.cannoliworld.models.Cannoli;
import nl.novi.cannoliworld.service.CannoliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/cannolis")
public class CannoliController {

    private final CannoliService cannoliService;
    private final PhotoController photoController;

    @Autowired
    public CannoliController(CannoliService cannoliService, PhotoController photoController) {
        this.cannoliService = cannoliService;
        this.photoController = photoController;
    }

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

        return cannoliList.stream()
                .map(CannoliDto::fromCannoli)
                .toList();
    }

    @GetMapping("/{id}")
    public CannoliDto getCannoli(@PathVariable("id") Long id) {
        var cannoli = cannoliService.getCannoli(id);
        return CannoliDto.fromCannoli(cannoli);
    }

    @PostMapping
    public ResponseEntity<CannoliDto> createCannoli(@Valid @RequestBody CannoliInputDto dto) {
        var saved = cannoliService.createCannoli(dto.toCannoli());
        var body = CannoliDto.fromCannoli(saved);
        return ResponseEntity
                .created(URI.create("/cannolis/" + saved.getId()))
                .body(body);
    }

    @PutMapping("/{id}")
    public CannoliDto updateCannoli(@PathVariable Long id,
                                    @Valid @RequestBody Cannoli updated) {
        var saved = cannoliService.updateCannoli(id, updated);
        return CannoliDto.fromCannoli(saved);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCannoli(@PathVariable("id") Long id) {
        cannoliService.deleteCannoli(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/image/{filename}")
    public ResponseEntity<Void> assignImageToCannoli(@PathVariable("id") Long cannoliId,
                                                     @PathVariable("filename") String fileName) {
        cannoliService.assignImageToCannoli(fileName, cannoliId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadImageToCannoli(@PathVariable("id") Long cannoliId,
                                                     @RequestParam("file") MultipartFile file) {
        photoController.singleFileUpload(file);
        cannoliService.assignImageToCannoli(file.getOriginalFilename(), cannoliId);
        return ResponseEntity.noContent().build();
    }
}




