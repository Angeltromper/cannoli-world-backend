package nl.novi.cannoliworld.controllers;

import nl.novi.cannoliworld.service.CannoliService;
import nl.novi.cannoliworld.service.PhotoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CannoliController.class)
class CannoliControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CannoliService cannoliService;

    @MockBean
    private PhotoService photoService;

    @Test
    void uploadImageToCannoli_returns204_andCallsServices() throws Exception {
        Long cannoliId = 42L;
        MockMultipartFile file = new MockMultipartFile(
                "file", "photo.jpg", MediaType.IMAGE_JPEG_VALUE, "bytes".getBytes()
        );

        Mockito.when(photoService.storeFile(any(), any())).thenReturn("photo.jpg");

        mockMvc.perform(
                multipart("/cannoli/{id}/image", cannoliId)
                        .file(file)
                        .with(req -> { req.setMethod("PUT"); return req; })
        ).andExpect(status().isNoContent());

        Mockito.verify(photoService).storeFile(any(), any());
        Mockito.verify(cannoliService).assignImageToCannoli(eq("photo.jpg"), eq(cannoliId));
    }

    @Test
    void assignImageByFileName_returns204_andCallsService() throws Exception {
        Long cannoliId = 7L;
        String fileName = "example.jpg";

        mockMvc.perform(
                put("/cannoli/{id}/image/{fileName}", cannoliId, fileName)
        ).andExpect(status().isNoContent());

        Mockito.verify(cannoliService).assignImageToCannoli(eq(fileName), eq(cannoliId));
    }
}
