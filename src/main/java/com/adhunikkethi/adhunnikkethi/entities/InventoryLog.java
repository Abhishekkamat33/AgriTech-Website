package com.adhunikkethi.adhunnikkethi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "change_type", nullable = false)
    private ChangeType changeType;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "change_date", nullable = false)
    private LocalDateTime changeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;


    @Column(name = "batch_number")
    private String batchNumber;

    @Column(name = "warehouse_location")
    private String warehouseLocation;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "unit")
    private String unit;

    @Column(name = "transaction_reference")
    private String transactionReference;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "cost")
    private BigDecimal cost;


    public enum ChangeType {
        ADD,
        REMOVE,
        SALE
    }

    @PrePersist
    protected void onCreate() {
        this.changeDate = LocalDateTime.now();
    }
}
