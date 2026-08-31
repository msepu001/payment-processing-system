package com.example.payment;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);

        PaymentRepository repository = new InMemoryPaymentRepository();

        PaymentService paymentService = new PaymentService(repository);

        /*try {

            Payment invalidPayment = new Payment(
                    "Pay-001",
                    new BigDecimal("250.00"),
                    PaymentStatus.COMPLETED
            );

        } catch (IllegalArgumentException exception) {

            System.out.println(
                    "Unable to create payment: " + exception.getMessage()
            );
        }*/

        paymentService.createPayment(new Payment("Pay-001",
                BigDecimal.valueOf(250.00),
                PaymentStatus.COMPLETED));

        paymentService.createPayment(new Payment("Pay-002",
                BigDecimal.valueOf(125.00),
                PaymentStatus.PENDING));

        paymentService.createPayment(new Payment("Pay-003",
                BigDecimal.valueOf(500.00),
                PaymentStatus.COMPLETED));

        paymentService.createPayment(new Payment("Pay-004",
                BigDecimal.valueOf(1000.00),
                PaymentStatus.COMPLETED));

        paymentService.createPayment(new Payment("Pay-005",
                BigDecimal.valueOf(75.00),
                PaymentStatus.PENDING));
        paymentService.createPayment(new Payment("Pay-006",
                BigDecimal.valueOf(150.50),
                PaymentStatus.FAILED));


        System.out.println("All payments:");
        paymentService.getAllPayments()
                .forEach(System.out::println);

        System.out.println(
                "Completed payment total: $" +
                        paymentService.getCompletedPaymentTotal()
        );

        System.out.println(
                "Failed payment count: " +
                        paymentService.getFailedPaymentCount()
        );

        System.out.println("Completed payments:");
        paymentService
                .getPaymentsByStatus(PaymentStatus.COMPLETED)
                .forEach(System.out::println);

        paymentService.findPaymentById("Pay-002")
                .ifPresentOrElse(
                        payment ->
                                System.out.println(
                                        "Found payment: " + payment
                                ),
                        () ->
                                System.out.println(
                                        "Payment not found"
                                )
                );
    }
}
