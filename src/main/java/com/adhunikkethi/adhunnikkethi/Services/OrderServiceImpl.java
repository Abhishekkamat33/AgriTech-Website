package com.adhunikkethi.adhunnikkethi.Services;

import com.adhunikkethi.adhunnikkethi.Dto.OrderDetailsDto;
import com.adhunikkethi.adhunnikkethi.Dto.OrderDto;
import com.adhunikkethi.adhunnikkethi.Dto.OrderResponseDto;
import com.adhunikkethi.adhunnikkethi.Respository.*;
import com.adhunikkethi.adhunnikkethi.entities.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ShippingRepository shippingRepository;
    private final PaymentRepository paymentRepository;
    private  final  OrderDetailsRepository orderDetailsRepository;
    private  final  ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            ShippingRepository shippingRepository,
                            PaymentRepository paymentRepository, OrderDetailsRepository orderDetailsRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.shippingRepository = shippingRepository;
        this.paymentRepository = paymentRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderDto orderDto) {
        Long userId = orderDto.getUserId();
        Long shippingId = orderDto.getShippingId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Shipping shipping = shippingRepository.findById(shippingId)
                .orElseThrow(() -> new RuntimeException("Shipping not found with id: " + shippingId));

        Order order = new Order();
        order.setUser(user);
        order.setShipping(shipping);
        order.setPayment(null); // set if available
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setStatus(Order.Status.PENDING); // or use status from DTO if provided
        order.setOrderDate(LocalDateTime.now());
        order.setComment(orderDto.getComment());

        if (orderDto.getOrderDetails() != null && !orderDto.getOrderDetails().isEmpty()) {
            List<OrderDetails> orderDetailsEntities = orderDto.getOrderDetails().stream()
                    .map(dto -> {
                        OrderDetails od = new OrderDetails();
                        od.setOrder(order);
                        od.setProduct(productRepository.findById(dto.getProductId())
                                .orElseThrow(() -> new RuntimeException("Product not found with id: " + dto.getProductId())));
                        od.setQuantity(dto.getQuantity());
                        od.setPrice(dto.getPrice());
                        return od;
                    }).collect(Collectors.toList());
            order.setOrderDetails(orderDetailsEntities);
        }

        Order savedOrder = orderRepository.save(order);

        return convertToResponseDto(savedOrder);
    }

    // Helper method to convert Order entity to OrderResponseDto
    private OrderResponseDto convertToResponseDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setOrderId(order.getOrderId());
        dto.setUserId(order.getUser() != null ? order.getUser().getUserId() : null);
        dto.setShippingId(order.getShipping() != null ? order.getShipping().getShippingId() : null);
        dto.setPaymentId(order.getPayment() != null ? order.getPayment().getPaymentId() : null);
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus() != null ? order.getStatus().name() : null);
        dto.setOrderDate(order.getOrderDate());
        dto.setComment(order.getComment());
        if (order.getOrderDetails() != null) {
            List<OrderDetailsDto> detailsDto = order.getOrderDetails().stream()
                    .map(od -> new OrderDetailsDto(
                            od.getOrderDetailsId(),
                            od.getOrder() != null ? od.getOrder().getOrderId() : null,
                            od.getProduct() != null ? od.getProduct().getProductId() : null,
                            od.getQuantity(),
                            od.getPrice()))
                    .collect(Collectors.toList());
            dto.setOrderDetails(detailsDto);
        }
        return dto;
    }


    @Override
    @Transactional
    public Optional<Order> updateOrder(Long id, Order orderDetails) {
        return orderRepository.findById(id).map(order -> {
            order.setTotalPrice(orderDetails.getTotalPrice());
            order.setStatus(orderDetails.getStatus());
            order.setComment(orderDetails.getComment());

            Long userId = orderDetails.getUser().getUserId();
            Long shippingId = orderDetails.getShipping().getShippingId();
            Long paymentId = orderDetails.getPayment().getPaymentId();

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
            Shipping shipping = shippingRepository.findById(shippingId)
                    .orElseThrow(() -> new RuntimeException("Shipping not found with id: " + shippingId));
            Payment payment = paymentRepository.findById(paymentId)
                    .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));

            order.setUser(user);
            order.setShipping(shipping);
            order.setPayment(payment);

            return  orderRepository.save(order);

        });
    }

    @Override
    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
