package com.adhunikkethi.adhunnikkethi.controllers;
import com.adhunikkethi.adhunnikkethi.Respository.OrderRepository;
import com.adhunikkethi.adhunnikkethi.Respository.PaymentRepository;
import com.adhunikkethi.adhunnikkethi.entities.Order;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.util.UriComponentsBuilder;
import java.math.BigDecimal;

import com.adhunikkethi.adhunnikkethi.Services.PaymentService;
import com.adhunikkethi.adhunnikkethi.entities.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final RestTemplate restTemplate;
    private final OrderRepository orderRepository;
    private final  PaymentRepository paymentRepository;

    @Autowired
    public PaymentController(PaymentService paymentService, RestTemplate restTemplate, OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.paymentService = paymentService;
        this.restTemplate = restTemplate;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }



    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/esewa/verify")
    public ResponseEntity<?> verifyEsewaPayment(@RequestBody Map<String, String> request) {
        String transactionUuid = request.get("transaction_uuid");
        String productCode = request.get("product_code");
        String totalAmount = request.get("total_amount");

        if (transactionUuid == null || productCode == null || totalAmount == null) {
            return ResponseEntity.badRequest().build();
        }

        String url = UriComponentsBuilder
                .fromHttpUrl("https://rc.esewa.com.np/api/epay/transaction/status/")
                .queryParam("product_code", productCode)
                .queryParam("total_amount", totalAmount)
                .queryParam("transaction_uuid", transactionUuid)
                .toUriString();

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Map<String, Object> responseBody = response.getBody();

            Payment payment = new Payment();

            payment.setTransactionId((String) responseBody.get("ref_id"));
            payment.setPaymentMethod("ESewa");

            String[] uuidParts = transactionUuid.split("-");
            String orderId = uuidParts[1];
            Long numericOrderId = Long.valueOf(orderId);
            Optional<Order> order = orderRepository.findById(numericOrderId);

            Order orderEntity = order.orElseThrow(() -> new EntityNotFoundException("Order not found"));

            payment.setOrder(orderEntity);

            Object amountObj = responseBody.get("total_amount");
            if (amountObj != null) {
                payment.setAmount(new BigDecimal(amountObj.toString()));
            }

            String statusString = (String) responseBody.get("status");
            if ("COMPLETE".equalsIgnoreCase(statusString)) {
                payment.setPaymentStatus(Payment.PaymentStatus.COMPLETED);
            } else if ("PENDING".equalsIgnoreCase(statusString)) {
                payment.setPaymentStatus(Payment.PaymentStatus.PENDING);
            } else if ("FAILED".equalsIgnoreCase(statusString)) {
                payment.setPaymentStatus(Payment.PaymentStatus.FAILED);
            } else if ("REFUNDED".equalsIgnoreCase(statusString)) {
                payment.setPaymentStatus(Payment.PaymentStatus.REFUNDED);
            } else {
                payment.setPaymentStatus(Payment.PaymentStatus.PENDING);
            }

            payment.setPaymentDate(LocalDateTime.now());

            if (paymentRepository.existsByOrder(orderEntity)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(payment);
            }


            Payment savedPayment = paymentRepository.save(payment);

            orderEntity.setPayment(savedPayment);
            orderRepository.save(orderEntity);

            return ResponseEntity.ok(savedPayment);
        } else {
            return ResponseEntity.status(500).build();
        }
    }




    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment paymentDetails) {
        return paymentService.updatePayment(id, paymentDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        boolean deleted = paymentService.deletePayment(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
