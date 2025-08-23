package com.adhunikkethi.adhunnikkethi.Services;

import com.adhunikkethi.adhunnikkethi.Dto.OrderDto;
import com.adhunikkethi.adhunnikkethi.Dto.OrderResponseDto;
import com.adhunikkethi.adhunnikkethi.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<OrderResponseDto> getAllOrders();
    Optional<Order> getOrderById(Long id);
    OrderResponseDto createOrder(OrderDto orderdto);
    Optional<Order> updateOrder(Long id, Order orderDetails);
    boolean deleteOrder(Long id);
}
