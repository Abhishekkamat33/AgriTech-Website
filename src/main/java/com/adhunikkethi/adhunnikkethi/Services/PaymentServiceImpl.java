package com.adhunikkethi.adhunnikkethi.Services;

import com.adhunikkethi.adhunnikkethi.Respository.OrderRepository;
import com.adhunikkethi.adhunnikkethi.Respository.PaymentRepository;
import com.adhunikkethi.adhunnikkethi.entities.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    @Transactional
    public Payment createPayment(Payment payment) {
        Long orderId = payment.getOrder().getOrderId();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        payment.setOrder(order);

        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public Optional<Payment> updatePayment(Long id, Payment paymentDetails) {
        return paymentRepository.findById(id).map(payment -> {
            payment.setAmount(paymentDetails.getAmount());
            payment.setPaymentMethod(paymentDetails.getPaymentMethod());
            payment.setPaymentStatus(paymentDetails.getPaymentStatus());
            payment.setTransactionId(paymentDetails.getTransactionId());

            Long orderId = paymentDetails.getOrder().getOrderId();
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
            payment.setOrder(order);

            return paymentRepository.save(payment);
        });
    }

    @Override
    public boolean deletePayment(Long id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
