package com.adhunikkethi.adhunnikkethi.controllers;

import com.adhunikkethi.adhunnikkethi.Dto.ProductReviewDto;
import com.adhunikkethi.adhunnikkethi.Services.ProductReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-reviews")
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    public ProductReviewController(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }

    @GetMapping
    public List<ProductReviewDto> getAllProductReviews() {
        return productReviewService.getAllProductReviews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductReviewDto> getProductReviewById(@PathVariable Long id) {
        return productReviewService.getProductReviewById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductReviewDto> createProductReview(@RequestBody ProductReviewDto productReviewDto) {
        ProductReviewDto created = productReviewService.createProductReview(productReviewDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductReviewDto> updateProductReview(@PathVariable Long id, @RequestBody ProductReviewDto reviewDto) {
        return productReviewService.updateProductReview(id, reviewDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductReview(@PathVariable Long id) {
        boolean deleted = productReviewService.deleteProductReview(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
