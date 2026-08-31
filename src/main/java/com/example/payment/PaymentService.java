package com.example.payment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void createPayment(Payment payment) {

        boolean paymentExists =
                paymentRepository.findById(payment.getId())
                        .isPresent();

        if (paymentExists) {
            throw new InvalidPaymentException(
                    "Payment ID already exists: "
                            + payment.getId()
            );
        }

        paymentRepository.save(payment);
    }

   public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }


public Optional<Payment> findPaymentById(String id) {
        return paymentRepository.findById(id);
}

    public List<Payment> getCompletedPayments() {
        return paymentRepository.findAll().stream()
                .filter(payment ->
                        payment.getStatus()
                                == PaymentStatus.COMPLETED)
                .toList();
    }

    public BigDecimal getCompletedPaymentTotal() {
        return paymentRepository.findAll().stream()
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
        return paymentRepository.findAll().stream()
                .filter(payment ->
                        payment.getStatus()
                                == PaymentStatus.FAILED)
                .count();
    }

    public List<Payment> getPaymentsByStatus(
            PaymentStatus status
    ) {
        return paymentRepository.findAll().stream()
                .filter(payment ->
                        payment.getStatus() == status)
                .toList();
    }
}