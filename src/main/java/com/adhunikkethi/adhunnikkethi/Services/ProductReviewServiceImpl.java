package com.adhunikkethi.adhunnikkethi.Services;

import com.adhunikkethi.adhunnikkethi.Dto.ProductReviewDto;
import com.adhunikkethi.adhunnikkethi.Respository.ProductReviewRepository;
import com.adhunikkethi.adhunnikkethi.Respository.UserRepository;
import com.adhunikkethi.adhunnikkethi.Respository.ProductRepository;
import com.adhunikkethi.adhunnikkethi.entities.ProductReview;
import com.adhunikkethi.adhunnikkethi.entities.User;
import com.adhunikkethi.adhunnikkethi.entities.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewRepository productReviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ProductReviewServiceImpl(ProductReviewRepository productReviewRepository,
                                    UserRepository userRepository,
                                    ProductRepository productRepository) {
        this.productReviewRepository = productReviewRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    private ProductReviewDto convertToDto(ProductReview pr) {
        ProductReviewDto dto = new ProductReviewDto();
        dto.setReviewId(pr.getReviewId());
        dto.setUserId(pr.getUser().getUserId());
        dto.setUserName(pr.getUser().getName());
        dto.setProductId(pr.getProduct().getProductId());
        dto.setProductName(pr.getProduct().getName());
        dto.setRating(pr.getRating());
        dto.setReviewText(pr.getReviewText());
        dto.setReviewDate(pr.getReviewDate());
        return dto;
    }

    private ProductReview convertToEntity(ProductReviewDto dto) {
        ProductReview pr = new ProductReview();
        pr.setRating(dto.getRating());
        pr.setReviewText(dto.getReviewText());

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + dto.getProductId()));

        pr.setUser(user);
        pr.setProduct(product);

        return pr;
    }

    @Override
    public List<ProductReviewDto> getAllProductReviews() {
        return productReviewRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductReviewDto> getProductReviewById(Long id) {
        return productReviewRepository.findById(id).map(this::convertToDto);
    }

    @Override
    @Transactional
    public ProductReviewDto createProductReview(ProductReviewDto productReviewDto) {
        ProductReview entity = convertToEntity(productReviewDto);
        ProductReview saved = productReviewRepository.save(entity);
        return convertToDto(saved);
    }

    @Override
    @Transactional
    public Optional<ProductReviewDto> updateProductReview(Long id, ProductReviewDto productReviewDto) {
        return productReviewRepository.findById(id).map(existing -> {
            existing.setRating(productReviewDto.getRating());
            existing.setReviewText(productReviewDto.getReviewText());

            User user = userRepository.findById(productReviewDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + productReviewDto.getUserId()));
            Product product = productRepository.findById(productReviewDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productReviewDto.getProductId()));

            existing.setUser(user);
            existing.setProduct(product);

            ProductReview saved = productReviewRepository.save(existing);
            return convertToDto(saved);
        });
    }

    @Override
    public boolean deleteProductReview(Long id) {
        if (productReviewRepository.existsById(id)) {
            productReviewRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
