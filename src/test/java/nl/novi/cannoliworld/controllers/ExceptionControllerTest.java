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

    @InjectMocks
    private ExceptionController exceptionController;

    @Test
    @DisplayName(
            "Should return a response entity with the message and status code when the exception is RecordNotFoundException")
    void exceptionWhenRecordNotFoundExceptionThenReturnResponseEntityWithMessageAndStatusCode() {

        String message = "Record not found";
        RecordNotFoundException recordNotFoundException = new RecordNotFoundException(message);

        ResponseEntity<Object> responseEntity =
                exceptionController.exception(recordNotFoundException);



        assertEquals(message, responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

}






