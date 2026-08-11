package com.example.payment;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class PaymentApplication {

    public static void main(String[] args){

        Payment payment = new Payment("Pay-001", 250.00, "COMPLETED" );
        Payment payment2 = new Payment("Pay-002", 125.00, "PENDING" );
        Payment payment3 = new Payment("Pay-003", 500.00, "COMPLETED" );
        Payment payment4 = new Payment("Pay-004",1000.00 , "COMPLETED" );
        Payment payment5 = new Payment("Pay-005", 75.00, "PENDING" );


        List<Payment>  payments = new ArrayList<>();
        payments.add(payment);
        payments.add(payment2);
        payments.add(payment3);
        payments.add(payment4);
        payments.add(payment5);

        Map<String, Payment> paymentMap = new HashMap<>();

        paymentMap.put(payment.getId(), payment);
        paymentMap.put(payment2.getId(), payment2);
        paymentMap.put(payment3.getId(), payment3);
        paymentMap.put(payment4.getId(), payment4);
        paymentMap.put(payment5.getId(), payment5);

        paymentOptional.ifPresentOrElse(
                pay -> System.out.println(
                        "Payment found: " + pay.getId()
                ),
                () -> System.out.println(
                        "Payment not found"
                )
        );

        Payment foundPayment = paymentMap.get("Pay-003");

        System.out.println("Payment ID: " + foundPayment.getId()
        + " Amount: " + foundPayment.getAmount()
        + " Status: " + foundPayment.getStatus());

        long num = payments.stream()
                .filter(pay -> pay.getStatus().equals("COMPLETED"))
                .count();

        System.out.println("Number of completed Payments: " + num);

                double amount = payments.stream()
                        .filter(pay -> pay.getStatus().equals("COMPLETED"))
                        .mapToDouble(pay -> pay.getAmount())
                        .sum();

                System.out.println("The total is: " + amount);

                payments.stream()
                        .filter(pay -> pay.getStatus().equals("PENDING"))
                        .forEach(pay -> System.out.println(pay.getId()));

    }
}
