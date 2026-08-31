package com.example.payment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;

import java.util.List;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Payment API is running";
    }

  @GetMapping("/payments")
        public List<Payment> getAllPayment(){
        return paymentService.getAllPayments();
  }

  @PostMapping("/payments")
    public ResponseEntity<Payment> createPayment(
          @RequestBody @Valid CreatePaymentRequest request
    ) {

      Payment payment = new Payment(
              request.id(),
              request.amount(),
              request.status()
      );

        paymentService.createPayment(payment);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(payment);
    }

  //@GetMapping(("/payments/{id}"))
    //public Payment getPaymentId(@PathVariable String id){
      //  return paymentService.findPaymentById(id).orElse(null);
  //}

    @GetMapping("/payments/{id}")
    public ResponseEntity<Payment> getPaymentById(
            @PathVariable String id
    ) {

        return paymentService.findPaymentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

}
