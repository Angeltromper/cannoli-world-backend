package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.CannoliWorldApplication;
import nl.novi.cannoliworld.repositories.FileUploadRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;


public class PhotoService {
    @Value("${my.upload_location}")
    private Path fileStoragePath;

    private final String fileStorageLocation;

    private final FileUploadRepository repo;

    public PhotoService(@Value("${my.upload_location}") String fileStorageLocation, FileUploadRepository repo) {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();

        this.fileStorageLocation = fileStorageLocation;
        this.repo = repo;

        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Issue in creating file directory");
        }

    }

    public String storeFile(MultipartFile file, String url) {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        Path filePath = Paths.get(fileStoragePath + "\\" + fileName);
        //change for windows to two backslashes: "\\" + fileName. For mac it's "/"

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Issue in storing the file", e);
        }
        repo.save(new FileUploadResponse(fileName, file.getContentType(), url));

        return fileName;
    }
}

