package com.example.payment;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryPaymentRepositoryTest {

    @Test
    void shouldSaveAndFindPaymentById() {

        PaymentRepository repository =
                new InMemoryPaymentRepository();

        Payment payment = new Payment(
                "Pay-001",
                new BigDecimal("250.00"),
                PaymentStatus.COMPLETED
        );

        repository.save(payment);

        Optional<Payment> result =
                repository.findById("Pay-001");

        assertTrue(result.isPresent());
        assertEquals("Pay-001", result.get().getId());
        assertEquals(
                new BigDecimal("250.00"),
                result.get().getAmount()
        );
    }
}
