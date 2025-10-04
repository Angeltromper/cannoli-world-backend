package nl.novi.cannoliworld.service;

import lombok.extern.slf4j.Slf4j;
import nl.novi.cannoliworld.models.FileUploadResponse;
import nl.novi.cannoliworld.repositories.FileUploadRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Slf4j
@Service
public class PhotoService {

    private final Path fileStoragePath;
    private final FileUploadRepository fileUploadRepository;

    public PhotoService(
            @Value("${my.upload_location}") String fileStorageLocation,
            FileUploadRepository fileUploadRepository
    ) {
        this.fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileUploadRepository = fileUploadRepository;
    }

    @PostConstruct
    void initDir() {
        try {
            Files.createDirectories(this.fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Kon uploadmap niet aanmaken: " + fileStoragePath, e);
        }
    }

    public String storeFile(MultipartFile file, String url) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (fileName.contains("..")) {
            throw new IllegalArgumentException("Ongeldige bestandsnaam: " + fileName);
        }
        Path target = fileStoragePath.resolve(fileName).normalize();
        try {
            Files.createDirectories(target.getParent());
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("I/O fout bij opslaan '{}' naar {}: {}", fileName, target, e.getMessage(), e);
            throw new RuntimeException("Kon bestand niet opslaan", e);
        }

        fileUploadRepository.findById(fileName)
                .map(existing -> {
                    existing.setContentType(file.getContentType());
                    existing.setUrl(url);
                    return fileUploadRepository.save(existing);
                })
                .orElseGet(() ->
                        fileUploadRepository.save(new FileUploadResponse(fileName, file.getContentType(), url))
                );

        return fileName;
    }

    public Resource downloadFile(String fileName) {
        if (fileName.contains("..")) {
            throw new IllegalArgumentException("Ongeldige bestandsnaam: " + fileName);
        }
        Path path = fileStoragePath.resolve(fileName).normalize();
        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
        } catch (MalformedURLException ignored) { }
        throw new RuntimeException("Bestand bestaat niet of is niet leesbaar: " + fileName);
    }

    public void deleteImage(String fileName) {
        fileUploadRepository.deleteById(fileName);
        try {
            Files.deleteIfExists(fileStoragePath.resolve(fileName).normalize());
        } catch (IOException ignored) { }
    }
}
