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

    @Value("${my.upload_location}")
    private Path fileStoragePath;

    private final String fileStorageLocation;
    private final FileUploadRepository fileUploadRepository;

    public PhotoService(@Value("${my.upload_location}") String fileStorageLocation,
                        FileUploadRepository fileUploadRepository) {
        this.fileStorageLocation = fileStorageLocation;
        this.fileUploadRepository = fileUploadRepository;
        this.fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Issue in creating file directory", e);
        }
    }

    public String storeFile(MultipartFile file, String url) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path filePath = fileStoragePath.resolve(fileName).normalize();
        try {
            Files.createDirectories(filePath.getParent());
            try (var in = file.getInputStream()) {
                Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            log.error("I/O error saving '{}' to {}: {}", fileName, filePath, e.getMessage(), e);
            throw new RuntimeException("Issue in storing the file", e);
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

    public Resource downLoadFile(String fileName) {
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName).normalize();
        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
        } catch (MalformedURLException ignored) { }
        throw new RuntimeException("the file doesn't exist or not readable");
    }

    public void deleteImage(String fileName) {
        fileUploadRepository.deleteById(fileName);
        try {
            Files.deleteIfExists(fileStoragePath.resolve(fileName).normalize());
        } catch (IOException ignored) { }
    }
}
