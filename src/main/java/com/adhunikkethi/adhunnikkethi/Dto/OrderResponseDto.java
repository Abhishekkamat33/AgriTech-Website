package com.adhunikkethi.adhunnikkethi.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private Long orderId;
    private Long userId;
    private Long shippingId;
    private Long paymentId;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime orderDate;
    private String comment;
    private List<OrderDetailsDto> orderDetails;
}
