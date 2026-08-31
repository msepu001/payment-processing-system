package com.example.payment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

    @RestControllerAdvice
    public class PaymentExceptionHandler {

        @ExceptionHandler(InvalidPaymentException.class)
        public ResponseEntity<String> handleInvalidPayment(
                InvalidPaymentException exception
        )
            {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Map.of(
                                "error", "Invalid payment",
                                "message", exception.getMessage()
                        ));
            }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationError(
                MethodArgumentNotValidException exception
        ) {

            String message = exception
                    .getBindingResult()
                    .getFieldErrors()
                    .getFirst()
                    .getDefaultMessage();

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "error", "Validation failed",
                            "message", message
                    ));
        }

    }
