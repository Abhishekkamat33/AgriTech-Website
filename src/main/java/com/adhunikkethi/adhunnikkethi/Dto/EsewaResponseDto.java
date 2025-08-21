package com.adhunikkethi.adhunnikkethi.Dto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsewaResponseDto {

    private String amount;

    private String failure_url;

    private String product_delivery_charge;

    private String product_service_charge;

    private String product_code;
    private String signature;
    private String signed_field_names;

    private String success_url;

    private String tax_amount;
    private String total_amount;
    private String transaction_uuid;
    private String payment_url;
}
