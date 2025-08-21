package com.adhunikkethi.adhunnikkethi.Respository;

import com.adhunikkethi.adhunnikkethi.entities.Order;
import com.adhunikkethi.adhunnikkethi.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByOrder(Order order);


}
