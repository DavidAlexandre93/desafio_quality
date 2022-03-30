package br.com.meli.desafio_quality.exception;

import br.com.meli.desafio_quality.dto.ExceptionPayloadDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        String errorMessage = fieldErrors.get(0).getDefaultMessage();
        ExceptionPayloadDTO exceptionPayload = ExceptionPayloadDTO.builder()
                .timestamp(LocalDateTime.now())
                .title("Erro de validação")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .description(errorMessage)
                .build();

        return ResponseEntity.badRequest().body(exceptionPayload);
    }
}
