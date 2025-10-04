package nl.novi.cannoliworld.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CannoliTest {

    private Cannoli cannoli;

    @BeforeEach
    void setUp() {
        cannoli = new Cannoli();
    }

    @Test
    @DisplayName("Setters vullen velden; getters geven zelfde waarden terug")
    void settersAndGettersWork() {
        FileUploadResponse image = new FileUploadResponse("fileName", "contentType", "url");

        cannoli.setId(1L);
        cannoli.setCannoliName("test");
        cannoli.setCannoliType("Glutenfree");
        cannoli.setDescription("This is a description");
        cannoli.setIngredients("ingredients");
        cannoli.setPrice(10.0);
        cannoli.setImage(image);

        assertAll(
                () -> assertEquals(1L, cannoli.getId()),
                () -> assertEquals("test", cannoli.getCannoliName()),
                () -> assertEquals("Glutenfree", cannoli.getCannoliType()),
                () -> assertEquals("This is a description", cannoli.getDescription()),
                () -> assertEquals("ingredients", cannoli.getIngredients()),
                () -> assertEquals(10.0, cannoli.getPrice(), 0.000001),
                () -> assertEquals(image, cannoli.getImage())
        );
    }
}