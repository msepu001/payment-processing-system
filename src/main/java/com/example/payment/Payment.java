package com.example.payment;

import java.math.BigDecimal;

/*
 * Payment
 *
 * This is our domain model and now also a JPA entity.
 *
 * @Entity tells Hibernate that Payment should be stored
 * in a database table.
 *
 * @Id identifies the primary key.
 *
 * Hibernate also requires a no-argument constructor so it can
 * recreate Payment objects when reading rows from PostgreSQL.
 */
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/*
 * JPA enum imports.
 *
 * EnumType.STRING tells Hibernate to store readable enum names
 * such as COMPLETED instead of numeric enum positions.
 */
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Payment {

    @Id
    private String id;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    protected Payment(){
        // Required by JPA/Hibernate.
    }

    public Payment(String id, BigDecimal amount, PaymentStatus status){

        if (id == null || id.isBlank()) {
            throw new InvalidPaymentException(
                    "id can not be blank"
            );
        }

        if(amount == null){
            throw new InvalidPaymentException(
                    "Payment amount cannot be null"
            );
        }

        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidPaymentException(
                "Payment amount must be greater than zero"
        );
        }

        if (status == null) {
            throw new InvalidPaymentException(
                    "Status can not be null"
            );
        }
        this.id = id;
        this.amount = amount;
        this.status = status;
    }

    public String getId(){
        return id;
    }

    public BigDecimal getAmount(){
        return amount;
    }

    public PaymentStatus getStatus(){
        return status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }
}
