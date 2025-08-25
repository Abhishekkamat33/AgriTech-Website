package com.adhunikkethi.adhunnikkethi.Respository;

import com.adhunikkethi.adhunnikkethi.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.orderDetails")
    List<Order> findAllWithOrderDetails();
}
