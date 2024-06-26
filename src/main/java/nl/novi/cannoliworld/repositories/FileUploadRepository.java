package nl.novi.cannoliworld.repositories;

import nl.novi.cannoliworld.service.FileUploadResponse;

public interface FileUploadRepository {
    void save(FileUploadResponse fileUploadResponse);
}
