package nl.novi.cannoliworld.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.novi.cannoliworld.service.PhotoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;


@ExtendWith(MockitoExtension.class)
class PhotoControllerTest {

    @Mock
    private PhotoService photoService;

    @InjectMocks
    private PhotoController photoController;

    private String fileStorageLocation = "src/test/resources/";

    @Test
    @DisplayName("Should throw an exception when the file does nog exist")
    void downloadSingleFileWhenFileDoesNotExistThenThrowException() {
        String fileName = "test.txt";
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(photoService.downLoadFile(fileName))
                .thenThrow(new RuntimeException("then file doesn't exist or not readable"));

        assertThrows(
                RuntimeException.class,
                () -> photoController.downLoadSingleFile(fileName, request));

    }

}