package com.adhunikkethi.adhunnikkethi.Services;

import com.adhunikkethi.adhunnikkethi.Respository.*;
import com.adhunikkethi.adhunnikkethi.entities.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryLogServiceImpl implements InventoryLogService {

    private final InventoryLogRepository inventoryLogRepository;
    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    public InventoryLogServiceImpl(InventoryLogRepository inventoryLogRepository,
                                   ProductRepository productRepository,
                                   AdminRepository adminRepository) {
        this.inventoryLogRepository = inventoryLogRepository;
        this.productRepository = productRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public List<InventoryLog> getAllInventoryLogs() {
        return inventoryLogRepository.findAll();
    }

    @Override
    public Optional<InventoryLog> getInventoryLogById(Long id) {
        return inventoryLogRepository.findById(id);
    }

    @Override
    @Transactional
    public InventoryLog createInventoryLog(InventoryLog inventoryLog) {
        Long productId = inventoryLog.getProduct().getProductId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        inventoryLog.setProduct(product);

        if (inventoryLog.getAdmin() != null) {
            Long adminId = inventoryLog.getAdmin().getAdminId();
            Admin admin = adminRepository.findById(adminId)
                    .orElseThrow(() -> new RuntimeException("Admin not found with id: " + adminId));
            inventoryLog.setAdmin(admin);
        }

        return inventoryLogRepository.save(inventoryLog);
    }

    @Override
    @Transactional
    public Optional<InventoryLog> updateInventoryLog(Long id, InventoryLog inventoryLogDetails) {
        return inventoryLogRepository.findById(id).map(inventoryLog -> {
            inventoryLog.setChangeType(inventoryLogDetails.getChangeType());
            inventoryLog.setQuantity(inventoryLogDetails.getQuantity());

            Long productId = inventoryLogDetails.getProduct().getProductId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
            inventoryLog.setProduct(product);

            if (inventoryLogDetails.getAdmin() != null) {
                Long adminId = inventoryLogDetails.getAdmin().getAdminId();
                Admin admin = adminRepository.findById(adminId)
                        .orElseThrow(() -> new RuntimeException("Admin not found with id: " + adminId));
                inventoryLog.setAdmin(admin);
            } else {
                inventoryLog.setAdmin(null);
            }

            return inventoryLogRepository.save(inventoryLog);
        });
    }

    @Override
    public boolean deleteInventoryLog(Long id) {
        if (inventoryLogRepository.existsById(id)) {
            inventoryLogRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
