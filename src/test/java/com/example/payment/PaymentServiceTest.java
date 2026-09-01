package com.example.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.mockito.Mockito;

public class PaymentServiceTest {

    private JpaPaymentRepository paymentRepository;
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {

        // Create a fake repository.
        paymentRepository = Mockito.mock(JpaPaymentRepository.class);

        // Inject the fake repository into the service.
        paymentService = new PaymentService(paymentRepository);
}

    @Test
    void shouldCalculateCompletedPaymentTotal() {

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
