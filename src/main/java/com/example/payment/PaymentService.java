package com.example.payment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    /*
     * PaymentService
     *
     * This service now uses JpaPaymentRepository instead of our
     * in-memory repository.
     *
     * JpaPaymentRepository is implemented automatically by Spring Data JPA.
     * Calls such as save(), findAll(), findById(), and existsById()
     * will ultimately execute SQL against PostgreSQL.
     */

    private final JpaPaymentRepository paymentRepository;

    public PaymentService(JpaPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void createPayment(Payment payment) {

        if (paymentRepository.existsById(payment.getId())) {
                throw new InvalidPaymentException(
                        "Payment ID already exists: " + payment.getId()
                );
            }

           // paymentRepository.save(payment);
       // }
       // boolean paymentExists =
             //   paymentRepository.findById(payment.getId())
               //         .isPresent();

       // if (paymentExists) {
        //    throw new InvalidPaymentException(
                   // "Payment ID already exists: "
                    //        + payment.getId()
           // );
       // }

        paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

  // public List<Payment> getAllPayments(){
       // return paymentRepository.findAll();
   // }


    public Optional<Payment> findPaymentById(String id) {
        return paymentRepository.findById(id);
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
        return paymentRepository.findByStatus(status);
    }

    public Optional<Payment> updatePayment(
            String id,
            BigDecimal amount,
            PaymentStatus status
    ) {
        Optional<Payment> existingPayment =
                paymentRepository.findById(id);

        if (existingPayment.isEmpty()) {
            return Optional.empty();
        }
        Payment updatePayment = new Payment(
                id,
                amount,
                status);

        Payment savedPayment =
                paymentRepository.save(updatePayment);

        return Optional.of(savedPayment);
    }

    public boolean deletePayment (String id){

            if (!paymentRepository.existsById(id)){
                return false;
            }
            paymentRepository.deleteById(id);
            return true;

        }

}