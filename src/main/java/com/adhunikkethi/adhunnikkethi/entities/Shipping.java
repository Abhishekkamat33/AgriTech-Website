package com.adhunikkethi.adhunnikkethi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shippingId;



    @Column(name = "FirstName")
    private  String FirstName;

    @Column(name = "LastName")
    private  String LastName;

    @Column(name = "Email")
    private  String Email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 20)
    private String zipCode;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShippingStatus shippingStatus;

    @Column(name = "shipping_date")
    private LocalDateTime shippingDate;


    public enum ShippingStatus {
        PENDING,
        SHIPPED,
        DELIVERED,
        CANCELLED
    }

    @PrePersist
    protected void onCreate() {
        if (this.shippingStatus == null) {
            this.shippingStatus = ShippingStatus.PENDING;
        }
    }
}
