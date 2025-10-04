package nl.novi.cannoliworld.controllers;

import nl.novi.cannoliworld.service.PhotoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PhotoController.class)
class PhotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhotoService photoService;

    @Test
    void upload_returnsFileUploadResponse() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "photo.jpg", MediaType.IMAGE_JPEG_VALUE, "test-bytes".getBytes()
        );
        Mockito.when(photoService.storeFile(any(), any())).thenReturn("photo.jpg");

        mockMvc.perform(multipart("/photos/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fileName", is("photo.jpg")))
                .andExpect(jsonPath("$.contentType", is(MediaType.IMAGE_JPEG_VALUE)))
                .andExpect(jsonPath("$.url", is("http://localhost/photos/download/photo.jpg")));
    }

    @Test
    void download_returnsResourceStream() throws Exception {
        byte[] bytes = "image-binary".getBytes();
        ByteArrayResource resource = new ByteArrayResource(bytes) {
            @Override public String getFilename() { return "photo.jpg"; }
        };
        Mockito.when(photoService.downloadFile(eq("photo.jpg"))).thenReturn(resource);

        mockMvc.perform(get("/photos/download/{fileName}", "photo.jpg"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "inline; filename=\"photo.jpg\""))
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(content().bytes(bytes));
    }
}
