package com.adhunikkethi.adhunnikkethi.Services;

import com.adhunikkethi.adhunnikkethi.Respository.CategoryRepository;
import com.adhunikkethi.adhunnikkethi.Respository.ManufacturerRepository;
import com.adhunikkethi.adhunnikkethi.Respository.ProductRepository;
import com.adhunikkethi.adhunnikkethi.Services.ProductService;
import com.adhunikkethi.adhunnikkethi.entities.Category;
import com.adhunikkethi.adhunnikkethi.entities.Manufacturer;
import com.adhunikkethi.adhunnikkethi.entities.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ManufacturerRepository manufacturerRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ManufacturerRepository manufacturerRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }


    @Override
    @Transactional
    public Product createProduct(Product product) {
        Long categoryId = product.getCategory().getCategoryId();
        Long manufacturerId = product.getManufacturer().getManufacturerId();
        String productName = product.getName().trim();

        boolean existingProduct = productRepository.findByName(productName).isPresent();
        if (existingProduct) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Item already exists");
        } else {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
            Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId)
                    .orElseThrow(() -> new RuntimeException("Manufacturer not found with id: " + manufacturerId));

            product.setCategory(category);
            product.setManufacturer(manufacturer);

            try {
                return productRepository.save(product);
            } catch (org.springframework.dao.DataIntegrityViolationException ex) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Item already exists (DB constraint)");
            }
        }
    }



    @Override
    @Transactional
    public Optional<Product> updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setStock(productDetails.getStock());
            product.setImage(productDetails.getImage());
            product.setStatus(productDetails.getStatus());

            Long categoryId = productDetails.getCategory().getCategoryId();
            Long manufacturerId = productDetails.getManufacturer().getManufacturerId();

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
            Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId)
                    .orElseThrow(() -> new RuntimeException("Manufacturer not found with id: " + manufacturerId));

            product.setCategory(category);
            product.setManufacturer(manufacturer);

            return productRepository.save(product);
        });
    }

    @Override
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
