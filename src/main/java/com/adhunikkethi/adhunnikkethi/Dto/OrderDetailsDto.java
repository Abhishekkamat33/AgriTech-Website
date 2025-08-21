// OrderDetailsDto.java
package com.adhunikkethi.adhunnikkethi.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {

    private Long orderDetailsId;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;

}
