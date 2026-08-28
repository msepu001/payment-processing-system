package com.example.payment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

public class PaymentService {

    private final List<Payment> payments = new ArrayList<>();

    public void createPayment(Payment payment) {

        boolean paymentExists = payments.stream()
                .anyMatch(existingPayment ->
                        existingPayment.getId()
                                .equals(payment.getId()));

        if (paymentExists) {
            throw new InvalidPaymentException(
                    "Payment ID already exists: "
                            + payment.getId()
            );
        }

        payments.add(payment);
    }

    public List<Payment> getAllPayments() {
        return List.copyOf(payments);
    }

    public Optional<Payment> findPaymentById(String id) {
        return payments.stream()
                .filter(payment ->
                        payment.getId().equals(id))
                .findFirst();
    }

    public List<Payment> getCompletedPayments() {
        return payments.stream()
                .filter(payment ->
                        payment.getStatus()
                                == PaymentStatus.COMPLETED)
                .toList();
    }

    public BigDecimal getCompletedPaymentTotal() {
        return payments.stream()
                .filter(payment ->
                        payment.getStatus()
                                == PaymentStatus.COMPLETED)
                .map(Payment::getAmount)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );
    }

    public long getFailedPaymentCount() {
        return payments.stream()
                .filter(payment ->
                        payment.getStatus()
                                == PaymentStatus.FAILED)
                .count();
    }

    public List<Payment> getPaymentsByStatus(
            PaymentStatus status
    ) {
        return payments.stream()
                .filter(payment ->
                        payment.getStatus() == status)
                .toList();
    }
}