package nl.novi.cannoliworld.controllers;

import nl.novi.cannoliworld.models.FileUploadResponse;
import nl.novi.cannoliworld.service.PhotoService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/pictures")
public class PhotoController {

    private final PhotoService service;

    public PhotoController(PhotoService service) { this.service = service; }

    @PutMapping("/upload")
    FileUploadResponse singleFileUpload(@RequestParam("file") MultipartFile file){

        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/pictures/download/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();

        String contentType = file.getContentType();

        String fileName = service.storeFile(file, url);

        return new FileUploadResponse(fileName, contentType, url);
    }

    @GetMapping("/download/{fileName}")
    ResponseEntity<Resource> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {

        Resource resource = (Resource) service.downLoadFile(fileName);
        String mimeType;

        try{
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }

    @DeleteMapping("/delete/")
    ResponseEntity<Objects> deletePicture(@PathVariable String fileName) {
        service.deletePicture(fileName);

        return ResponseEntity.noContent().build();
    }

}
