package com.adhunikkethi.adhunnikkethi.Respository;

import com.adhunikkethi.adhunnikkethi.entities.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}
