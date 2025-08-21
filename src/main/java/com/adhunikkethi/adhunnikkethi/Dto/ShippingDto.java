package com.adhunikkethi.adhunnikkethi.Dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingDto {
    private Long id;  // optional for updates

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "first name should required")
    private  String FirstName;

    @NotNull(message = "Last name should required")
    private  String LastName;

    @NotNull(message = "Email should required")
    private  String Email;

    @NotNull(message = "Total price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total price must be positive")
    private BigDecimal totalPrice;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotBlank(message = "State is mandatory")
    private String state;

    @NotBlank(message = "Zip Code is mandatory")
    private String zipCode;

    @NotBlank(message = "Country is mandatory")
    private String country;

    @NotBlank(message = "Phone is mandatory")
    @Pattern(regexp = "\\+?[0-9\\- ]+", message = "Phone number is invalid")
    private String phone;

    // shippingDate is optional, so no validation here
    private LocalDateTime shippingDate;

    @NotBlank(message = "Shipping status is mandatory")
    @Pattern(regexp = "PENDING|SHIPPED|DELIVERED|CANCELLED", message = "Invalid shipping status")
    private String shippingStatus;
}
