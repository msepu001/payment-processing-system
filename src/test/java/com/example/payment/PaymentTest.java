package com.example.payment;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentTest {

    @Test
    void shouldCreateValidPayment() {

        Payment payment = new Payment(
                "Pay-001",
                new BigDecimal("250.00"),
                PaymentStatus.COMPLETED
        );

        assertEquals("Pay-001", payment.getId());
        assertEquals(new BigDecimal("250.00"), payment.getAmount());
        assertEquals(PaymentStatus.COMPLETED, payment.getStatus());
    }
}