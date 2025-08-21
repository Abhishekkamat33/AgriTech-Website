package com.adhunikkethi.adhunnikkethi.Services;

import com.adhunikkethi.adhunnikkethi.Respository.*;
import com.adhunikkethi.adhunnikkethi.entities.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository,
                                   OrderRepository orderRepository,
                                   ProductRepository productRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }

    @Override
    public Optional<OrderDetails> getOrderDetailsById(Long id) {
        return orderDetailsRepository.findById(id);
    }

    @Override
    @Transactional
    public OrderDetails createOrderDetails(OrderDetails orderDetails) {
        Long orderId = orderDetails.getOrder().getOrderId();
        Long productId = orderDetails.getProduct().getProductId();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        orderDetails.setOrder(order);
        orderDetails.setProduct(product);

        return orderDetailsRepository.save(orderDetails);
    }

    @Override
    @Transactional
    public Optional<OrderDetails> updateOrderDetails(Long id, OrderDetails details) {
        return orderDetailsRepository.findById(id).map(orderDetails -> {
            orderDetails.setQuantity(details.getQuantity());
            orderDetails.setPrice(details.getPrice());

            Long orderId = details.getOrder().getOrderId();
            Long productId = details.getProduct().getProductId();

            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

            orderDetails.setOrder(order);
            orderDetails.setProduct(product);

            return orderDetailsRepository.save(orderDetails);
        });
    }

    @Override
    public boolean deleteOrderDetails(Long id) {
        if (orderDetailsRepository.existsById(id)) {
            orderDetailsRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
