package com.example.payment;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class PaymentApplication {

    public static void main(String[] args){

        Payment payment = new Payment("Pay-001", BigDecimal.valueOf(250.00), PaymentStatus.COMPLETED );
        Payment payment2 = new Payment("Pay-002", BigDecimal.valueOf(125.00), PaymentStatus.PENDING  );
        Payment payment3 = new Payment("Pay-003", BigDecimal.valueOf(500.00), PaymentStatus.COMPLETED );
        Payment payment4 = new Payment("Pay-004",BigDecimal.valueOf(1000.00) , PaymentStatus.COMPLETED  );
        Payment payment5 = new Payment("Pay-005", BigDecimal.valueOf(75.00), PaymentStatus.PENDING );
        Payment payment6 = new Payment("Pay-006", BigDecimal.valueOf(150.50), PaymentStatus.FAILED );
        try {

            Payment invalidPayment = new Payment(
                    "Pay-007",
                    new BigDecimal("-100.00"),
                    PaymentStatus.PENDING
            );

        } catch (IllegalArgumentException exception) {

            System.out.println(
                    "Unable to create payment: " + exception.getMessage()
            );
        }

        List<Payment>  payments = new ArrayList<>();
        payments.add(payment);
        payments.add(payment2);
        payments.add(payment3);
        payments.add(payment4);
        payments.add(payment5);
        payments.add(payment6);

        Map<String, Payment> paymentMap = new HashMap<>();

        paymentMap.put(payment.getId(), payment);
        paymentMap.put(payment2.getId(), payment2);
        paymentMap.put(payment3.getId(), payment3);
        paymentMap.put(payment4.getId(), payment4);
        paymentMap.put(payment5.getId(), payment5);
        paymentMap.put(payment6.getId(), payment6);

        Payment foundPayment = paymentMap.get("Pay-003");

        System.out.println("Payment ID: " + foundPayment.getId()
        + " Amount: " + foundPayment.getAmount()
        + " Status: " + foundPayment.getStatus());

        long num = payments.stream()
                .filter(pay -> pay.getStatus() == PaymentStatus.FAILED)
                .count();

        System.out.println("Number of Failed Payments: " + num);

                BigDecimal amount = payments.stream()
                        .filter(pay -> pay.getStatus() == PaymentStatus.FAILED)
                        .map(Payment::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                System.out.println("The total is: " + amount);

                payments.stream()
                        .filter(pay ->PaymentStatus.PENDING==(pay.getStatus()))
                        .forEach(pay -> System.out.println(pay.getId()));

    }
}
