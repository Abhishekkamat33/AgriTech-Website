package com.adhunikkethi.adhunnikkethi.Services;

import com.adhunikkethi.adhunnikkethi.Dto.ProductReviewDto;

import java.util.List;
import java.util.Optional;

public interface ProductReviewService {
    List<ProductReviewDto> getAllProductReviews();
    Optional<ProductReviewDto> getProductReviewById(Long id);
    ProductReviewDto createProductReview(ProductReviewDto productReviewDto);
    Optional<ProductReviewDto> updateProductReview(Long id, ProductReviewDto productReviewDto);
    boolean deleteProductReview(Long id);
}
