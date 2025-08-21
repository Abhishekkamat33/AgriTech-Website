package com.adhunikkethi.adhunnikkethi.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewDto {
    private Long reviewId;
    private Long userId;
    private String userName; // or more user info you want to expose
    private Long productId;
    private String productName; // or more product info
    private Integer rating;
    private String reviewText;
    private LocalDateTime reviewDate;

}
