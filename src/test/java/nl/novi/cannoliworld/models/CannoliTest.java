package nl.novi.cannoliworld.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CannoliTest {

    @Test
    @DisplayName("Should set the ingredients")
    void setIngredientsShouldSetTheIngredients() {
        Cannoli cannoli = new Cannoli();
        cannoli.setIngredients("ingredients");
        assertEquals("ingredients", cannoli.getIngredients());
    }

    @Test
    @DisplayName("Should set the description")
    void setDescriptionShouldSetTheDescription() {
        Cannoli cannoli = new Cannoli();
        cannoli.setDescription("description");
        assertEquals("description", cannoli.getDescription());
    }

    @Test
    @DisplayName("Should set the cannoli type")
    void setCannoliType() {
        Cannoli cannoli = new Cannoli();
        cannoli.setCannoliType("test");
        assertEquals("test", cannoli.getCannoliType());
    }

    @Test
    @DisplayName("Should set the cannoli name")
    void setCannoliName() {
        Cannoli cannoli = new Cannoli();
        cannoli.setCannoliName("test");
        assertEquals("test", cannoli.getCannoliName());
    }

    @Test
    @DisplayName("Should set the id")
    void setIdShouldSetId() {
        Cannoli cannoli = new Cannoli();
        cannoli.setId(1L);
        assertEquals(1L, cannoli.getId());
    }

    @Test
    @DisplayName("Should set the image")
    void setImageShouldSetTheImage() {
        Cannoli cannoli = new Cannoli();
        FileUploadResponse image = new FileUploadResponse("fileName", "contentType", "url");

        cannoli.setImage(image);

        assertEquals(image, cannoli.getImage());
    }

    @Test
    @DisplayName("Should return the image of the cannoli")
    void getImageShouldReturnTheImageOfTheCannoli() {
        Cannoli cannoli = new Cannoli();
        FileUploadResponse image = new FileUploadResponse("fileName", "contentType", "url");
        cannoli.setImage(image);

        assertEquals(image, cannoli.getImage());
    }

    @Test
    @DisplayName("Should return the price of the cannoli")
    void getPriceShouldReturnThePriceOfTheCannoli() {
        Cannoli cannoli = new Cannoli();
        cannoli.setPrice(10.0);
        assertEquals(10.0, cannoli.getPrice());
    }

    @Test
    @DisplayName("Should return the ingredients of the cannoli")
    void getIngredientsShouldReturnTheIngredientsOfTheCannoli() {
        Cannoli cannoli = new Cannoli();
        cannoli.setIngredients("ingredients");
        assertEquals("ingredients", cannoli.getIngredients());
    }

    @Test
    @DisplayName("Should return the description of the cannoli")
    void getDescriptionShouldReturnTheDescriptionOfTheCannoli() {
        Cannoli cannoli = new Cannoli();
        cannoli.setDescription("This is a description");
        assertEquals("This is a description", cannoli.getDescription());
    }

    @Test
    @DisplayName("Should return the cannoli type")
    void getCannoliTypeShouldReturnTheCannoli() {
        Cannoli cannoli = new Cannoli();
        cannoli.setCannoliType("Glutenfree");
        assertEquals("Glutenfree", cannoli.getCannoliType());
    }

    @Test
    @DisplayName("Should return the id of the cannoli")
    void getIdShouldReturnTheIdOfCannoli() {
        Cannoli cannoli = new Cannoli();
        cannoli.setId(1L);
        assertEquals(1L, cannoli.getId());
    }

    @Test
    @DisplayName("Should return the cannoli name")
    void getCannoliNameShouldReturnTheCannoliName() {
        Cannoli cannoli = new Cannoli();
        cannoli.setCannoliName("test");
        assertEquals("test", cannoli.getCannoliName());
    }
}
