package nl.novi.cannoliworld.controllers;

import nl.novi.cannoliworld.exceptions.BadRequestException;
import nl.novi.cannoliworld.exceptions.RecordNotFoundException;
import nl.novi.cannoliworld.exceptions.UsernameAlreadyExistException;
import nl.novi.cannoliworld.exceptions.UsernameNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {

    // 404
    @ExceptionHandler({RecordNotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<?> handleNotFound(RuntimeException ex, HttpServletRequest req) {
        return body(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    // 400 - eigen BadRequest of validatie
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequest(BadRequestException ex, HttpServletRequest req) {
        return body(HttpStatus.BAD_REQUEST, ex.getMessage(), req);
    }

    // 400 - @Valid field errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String, Object> payload = base(HttpStatus.BAD_REQUEST, "Validation failed", req);
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fe -> errors.put(fe.getField(), fe.getDefaultMessage()));
        payload.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload);
    }

    // 409 - unieke constraint / username bestaat al
    @ExceptionHandler({UsernameAlreadyExistException.class, DataIntegrityViolationException.class})
    public ResponseEntity<?> handleConflict(Exception ex, HttpServletRequest req) {
        return body(HttpStatus.CONFLICT, ex.getMessage(), req);
    }

    // 403
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleForbidden(AccessDeniedException ex, HttpServletRequest req) {
        return body(HttpStatus.FORBIDDEN, "Forbidden", req);
    }

    // 405
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex, HttpServletRequest req) {
        return body(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage(), req);
    }

    // 500 (laatste vangnet)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex, HttpServletRequest req) {
        return body(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), req);
    }

    /* ---------------- helpers ---------------- */

    private ResponseEntity<Map<String, Object>> body(HttpStatus status, String message, HttpServletRequest req) {
        Map<String, Object> payload = base(status, message, req);
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload);
    }

    private Map<String, Object> base(HttpStatus status, String message, HttpServletRequest req) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("timestamp", Instant.now().toString());
        m.put("status", status.value());
        m.put("error", status.getReasonPhrase());
        m.put("message", message);
        m.put("path", req.getRequestURI());
        return m;
    }
}
