package com.example.payment;

import java.math.BigDecimal;

public class Payment {
    private final String id;
    private final BigDecimal amount;
    private final PaymentStatus status;

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
}
