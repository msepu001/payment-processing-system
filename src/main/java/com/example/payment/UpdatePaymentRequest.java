package com.example.payment;

/*
 * UpdatePaymentRequest
 *
 * Purpose:
 * Represents JSON sent by a client when updating a Payment.
 *
 * The payment ID is NOT included here because the ID comes
 * from the URL:
 *
 * PUT /payments/Pay-001
 *
 * Bean Validation rejects null/non-positive amounts and
 * missing statuses before the controller calls the service.
 */

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdatePaymentRequest(

        @NotNull(message = "Payment amount cannot be null")
        @Positive(message = "Payment amount must be greater than zero")
        BigDecimal amount,

        @NotNull(message = "Payment status cannot be null")
        PaymentStatus status
) {
}
