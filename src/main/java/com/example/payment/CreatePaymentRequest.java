package com.example.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreatePaymentRequest(

        @NotBlank(message = "Payment ID cannot be blank")
        String id,

        @NotNull(message = "Payment amount cannot be null")
        @Positive(message = "Payment amount must be greater than zero")
        BigDecimal amount,

        @NotNull(message = "Payment status cannot be null")
        PaymentStatus status

) {
}
