package br.com.api.cadastroCliente.controllers.handlers;

import br.com.api.cadastroCliente.dto.CustomError;
import br.com.api.cadastroCliente.services.exceptions.ResourceNotFoundExcepetion;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundExcepetion.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundExcepetion e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError err = new CustomError(Instant.now(), status.toString(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
