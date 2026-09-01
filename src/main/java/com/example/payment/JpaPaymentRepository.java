package com.example.payment;

/*
 * JpaPaymentRepository
 *
 * This is a Spring Data JPA repository.
 *
 * JpaRepository gives us database operations automatically,
 * including:
 *
 * save(...)
 * findAll()
 * findById(...)
 * existsById(...)
 * deleteById(...)
 *
 * We do not have to write SQL or implement these methods ourselves.
 *
 * Payment = entity type
 * String  = type of Payment's @Id field
 */

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentRepository
        extends JpaRepository<Payment, String> {

    List<Payment> findByStatus(PaymentStatus status);
}
