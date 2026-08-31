package com.example.payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {

    void save(Payment payment);

    List<Payment> findAll();

    Optional<Payment> findById(String id);
}