package nl.novi.cannoliworld.controllers;

import lombok.RequiredArgsConstructor;
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
@RequestMapping("/images")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService service;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileUploadResponse> singleFileUpload(@RequestParam("file") MultipartFile file) {
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/download/")
                .pathSegment(Objects.requireNonNull(file.getOriginalFilename()))
                .toUriString();

        String stored = service.storeFile(file, url);
        return ResponseEntity.ok(new FileUploadResponse(stored, file.getContentType(), url));
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = service.downloadFile(fileName);

        String mimeType;
        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType != null ? mimeType : MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
