package nl.novi.cannoliworld.repositories;

import nl.novi.cannoliworld.models.FileUploadResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;
import java.util.stream.Stream;

public interface FileUploadRepository extends JpaRepository<FileUploadResponse, String> {

    Optional<FileUploadResponse> findByFileName(String fileName);
}

