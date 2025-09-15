package nl.novi.cannoliworld.controllers;

import nl.novi.cannoliworld.exeptions.RecordNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ExceptionControllerTest {

    private final ExceptionController exceptionController = new ExceptionController();

    @Test
    @DisplayName("RecordNotFoundException -> 404 met message")
    void exception_returnsNotFoundWithMessage() {
        String message = "Record not found";
        var ex = new RecordNotFoundException(message);

        ResponseEntity<Object> response = exceptionController.exception(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(message, (String) response.getBody());
    }
}




