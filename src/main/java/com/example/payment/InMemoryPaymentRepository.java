package com.example.payment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryPaymentRepository implements PaymentRepository {

    private final List<Payment> payments = new ArrayList<>();

    @Override
    public void save(Payment payment) {
        payments.add(payment);
    }

    @Override
    public List<Payment> findAll() {
        return List.copyOf(payments);
    }

    @Override
    public Optional<Payment> findById(String id) {
        return payments.stream()
                .filter(payment ->
                        payment.getId().equals(id))
                .findFirst();
    }
}
