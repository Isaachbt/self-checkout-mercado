package com.isaac.br.selfcheckoutmercado.exceptions.gobalException;

import com.isaac.br.selfcheckoutmercado.dto.ErrorResponse;
import com.isaac.br.selfcheckoutmercado.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(NotFoundException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(QuantityInvalidException.class)
    public ResponseEntity<ErrorResponse> quantityInvalid(QuantityInvalidException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.CONFLICT.value(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(NegadoException.class)
    public ResponseEntity<ErrorResponse> acaoNegada(NegadoException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(CartAllItemsException.class)
    public ResponseEntity<ErrorResponse> impossivelCancelarCheckout(CartAllItemsException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.CONFLICT.value(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> impossivelCancelarCheckout(InternalServerException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}
