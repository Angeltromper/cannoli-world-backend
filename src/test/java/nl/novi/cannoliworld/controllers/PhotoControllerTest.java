package nl.novi.cannoliworld.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.novi.cannoliworld.models.Cannoli;
import nl.novi.cannoliworld.models.DeliveryRequest;
import nl.novi.cannoliworld.models.FileUploadResponse;
import nl.novi.cannoliworld.models.User;
import nl.novi.cannoliworld.service.CannoliService;
import nl.novi.cannoliworld.service.PhotoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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