package com.adhunikkethi.adhunnikkethi.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EsewaRequestDto {

        private String amount;

        @JsonProperty("tax_amount")
        private String taxAmount;

        @JsonProperty("product_service_charge")
        private String productServiceCharge;

        @JsonProperty("product_delivery_charge")
        private String productDeliveryCharge;

        @JsonProperty("product_code")
        private String productCode;

        @JsonProperty("total_amount")
        private String totalAmount;

        @JsonProperty("transaction_uuid")
        private String transactionUuid;

        @JsonProperty("success_url")
        private String successUrl;

        @JsonProperty("failure_url")
        private String failureUrl;

        @JsonProperty("signed_field_names")
        private String signedFieldNames;

        private String signature;

        @JsonProperty("shipping_address")
        private Object shippingAddress; // Use a dedicated DTO if you want

        private Long orderId;
}
