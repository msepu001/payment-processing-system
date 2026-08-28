package com.example.payment;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentServiceTest {

    @Test
    void shouldCalculateCompletedPaymentTotal() {

        PaymentService paymentService = new PaymentService();

        paymentService.createPayment(
                new Payment(
                        "Pay-001",
                        new BigDecimal("250.00"),
                        PaymentStatus.COMPLETED
                )
        );

        paymentService.createPayment(
                new Payment(
                        "Pay-002",
                        new BigDecimal("125.00"),
                        PaymentStatus.PENDING
                )
        );

        paymentService.createPayment(
                new Payment(
                        "Pay-003",
                        new BigDecimal("500.00"),
                        PaymentStatus.COMPLETED
                )
        );

        BigDecimal total =
                paymentService.getCompletedPaymentTotal();

        assertEquals(
                new BigDecimal("750.00"),
                total
        );
    }

    @Test
    void shouldCountFailedPayments() {

        PaymentService paymentService = new PaymentService();

        paymentService.createPayment(
                new Payment(
                        "Pay-001",
                        new BigDecimal("125.00"),
                        PaymentStatus.FAILED
                )
        );

        paymentService.createPayment(
                new Payment(
                        "Pay-002",
                        new BigDecimal("125.00"),
                        PaymentStatus.FAILED
                )
        );

      long failedCount = paymentService.getFailedPaymentCount();

        System.out.println(paymentService.getFailedPaymentCount());
        assertEquals(2, failedCount);
    }

    @Test
    void shouldReturnEmptyWhenPaymentDoesNotExist() {

        PaymentService paymentService = new PaymentService();

        paymentService.createPayment(
                new Payment(
                        "Pay-001",
                        new BigDecimal("125.00"),
                        PaymentStatus.FAILED
                )
        );

        Optional<Payment> result =
                paymentService.findPaymentById("Pay-999");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnTrueForIdThatExist(){

        PaymentService paymentService = new PaymentService();

        paymentService.createPayment(
                new Payment(
                        "Pay-001",
                        new BigDecimal("125.00"),
                        PaymentStatus.FAILED
                )
        );

        paymentService.createPayment(
                new Payment(
                        "Pay-002",
                        new BigDecimal("125.00"),
                        PaymentStatus.FAILED
                )
        );

        paymentService.createPayment(
                new Payment(
                        "Pay-003",
                        new BigDecimal("125.00"),
                        PaymentStatus.FAILED
                )
        );



    }
}
