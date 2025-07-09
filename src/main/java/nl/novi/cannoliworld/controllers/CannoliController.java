package nl.novi.cannoliworld.controllers;

import nl.novi.cannoliworld.dtos.CannoliDto;
import nl.novi.cannoliworld.dtos.CannoliInputDto;
import nl.novi.cannoliworld.models.Cannoli;
import nl.novi.cannoliworld.service.CannoliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    @Transactional
    public List<CannoliDto> getCannolis(@RequestParam(value = "cannoliName", required = false, defaultValue = "")String cannoliName,
                                        @RequestParam(value = "cannoliType", required = false, defaultValue = "")String cannoliType) {

        var dtos = new ArrayList<CannoliDto>();
        List<Cannoli> cannoliList;


        if (cannoliName == null && cannoliType == null) {

            cannoliList = cannoliService.getCannolis();
        } else if (cannoliName != null && cannoliType == null) {

            cannoliList = cannoliService.findCannoliListByName(cannoliName);

        } else {

            cannoliList = cannoliService.findCannoliListByType(cannoliType);
        }

        var cannolis = cannoliService.getCannolis();
        for (Cannoli cannoli : cannolis) {
            dtos.add(CannoliDto.fromCannoli(cannoli));
        }
        return dtos;
    }

    @GetMapping("/{id}")
    public CannoliDto getCannoli(@PathVariable("id") Long id) {
        var cannoli = cannoliService.getCannoli(id);

        return CannoliDto.fromCannoli(cannoli);
    }
        
    @PostMapping("/create")
    public CannoliDto createCannoli(@RequestBody CannoliInputDto dto){
        var cannoli = cannoliService.createCannoli(dto.toCannoli());

        return CannoliDto.fromCannoli(cannoli);
    }

    @PutMapping("/{id}")
    public CannoliDto updateCannoli(@PathVariable Long id,
                                    @RequestBody Cannoli cannoli) {
        cannoliService.updateCannoli(cannoli);

        return CannoliDto.fromCannoli(cannoli);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Cannoli> deleteCannoli(@PathVariable("id") Long id) {

        cannoliService.deleteCannoli(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("cannoli/{id}/image/{filename}")
    public void assignImageToCannoli(@PathVariable("id") Long cannoliId,
                                     @PathVariable("filename") String fileName) {

        cannoliService.assignImageToCannoli(fileName, cannoliId);
    }

    @PutMapping("/{id}/image")
    public void uploadImageToCannoli(@PathVariable("id") Long cannoliId,
                                     @RequestBody MultipartFile file){

        photoController.singleFileUpload(file);
        cannoliService.assignImageToCannoli(file.getOriginalFilename(), cannoliId);
    }
}
        
        
     

    

