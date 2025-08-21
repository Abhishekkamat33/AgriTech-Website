package com.adhunikkethi.adhunnikkethi.Respository;

import com.adhunikkethi.adhunnikkethi.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
