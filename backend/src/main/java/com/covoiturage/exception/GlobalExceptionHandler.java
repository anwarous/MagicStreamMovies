package com.covoiturage.exception;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;import org.springframework.web.bind.MethodArgumentNotValidException;import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;import java.util.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String,Object>> notFound(ResourceNotFoundException ex){return build(HttpStatus.NOT_FOUND,ex.getMessage());}
  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<Map<String,Object>> unauthorized(UnauthorizedException ex){return build(HttpStatus.UNAUTHORIZED,ex.getMessage());}
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String,Object>> validation(MethodArgumentNotValidException ex){return build(HttpStatus.BAD_REQUEST, ex.getBindingResult().getAllErrors().stream().findFirst().map(e->e.getDefaultMessage()).orElse("Validation error"));}
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String,Object>> conflict(DataIntegrityViolationException ex){return build(HttpStatus.CONFLICT,"Data integrity violation (possibly duplicate email)");}
  private ResponseEntity<Map<String,Object>> build(HttpStatus status, String message){
    Map<String,Object> body=new LinkedHashMap<>();body.put("status",status.value());body.put("error",status.getReasonPhrase());body.put("message",message);body.put("timestamp", LocalDateTime.now().toString());
    return ResponseEntity.status(status).body(body);
  }
}
