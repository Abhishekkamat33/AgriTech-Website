package com.adhunikkethi.adhunnikkethi.Services;

import com.adhunikkethi.adhunnikkethi.entities.OrderDetails;

import java.util.List;
import java.util.Optional;

public interface OrderDetailsService {
    List<OrderDetails> getAllOrderDetails();
    Optional<OrderDetails> getOrderDetailsById(Long id);
    OrderDetails createOrderDetails(OrderDetails orderDetails);
    Optional<OrderDetails> updateOrderDetails(Long id, OrderDetails details);
    boolean deleteOrderDetails(Long id);
}
