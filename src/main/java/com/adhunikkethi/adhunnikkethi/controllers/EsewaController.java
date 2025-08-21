package com.adhunikkethi.adhunnikkethi.controllers;

import com.adhunikkethi.adhunnikkethi.Dto.EsewaRequestDto;
import com.adhunikkethi.adhunnikkethi.Dto.EsewaResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class EsewaController {

    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final String SECRET_KEY = "8gBm/:&EnhH.1/q"; // replace with your actual secret

    @PostMapping("/esewa")
    public ResponseEntity<EsewaResponseDto> initiatePayment(@RequestBody EsewaRequestDto requestDto) {
        try {
            // Calculate totalAmount = amount + taxAmount + productServiceCharge + productDeliveryCharge
            BigDecimal amount = new BigDecimal(requestDto.getAmount());
            BigDecimal taxAmount = new BigDecimal(requestDto.getTaxAmount());
            BigDecimal productDeliveryCharge = new BigDecimal(requestDto.getProductDeliveryCharge());
            BigDecimal productServiceCharge = new BigDecimal(requestDto.getProductServiceCharge());

            BigDecimal totalAmount = amount.add(taxAmount).add(productDeliveryCharge).add(productServiceCharge);
            String totalAmountStr = totalAmount.toPlainString();

            // Update the totalAmount in the request DTO for signing
            requestDto.setTotalAmount(totalAmountStr);

            String message = createSigningString(requestDto);
            String signature = hmacSha256Base64(message, SECRET_KEY);

            String signedFieldNames = "total_amount,transaction_uuid,product_code";

            EsewaResponseDto responseDto = new EsewaResponseDto();
            responseDto.setAmount(requestDto.getAmount());
            responseDto.setFailure_url(requestDto.getFailureUrl());
            responseDto.setProduct_code(requestDto.getProductCode());
            responseDto.setProduct_delivery_charge(requestDto.getProductDeliveryCharge());
            responseDto.setProduct_service_charge(requestDto.getProductServiceCharge());
            responseDto.setSignature(signature);
            responseDto.setSigned_field_names(signedFieldNames);
            responseDto.setSuccess_url(requestDto.getSuccessUrl());
            responseDto.setTax_amount(requestDto.getTaxAmount());
            responseDto.setTotal_amount(totalAmountStr);
            responseDto.setTransaction_uuid(requestDto.getTransactionUuid());
            responseDto.setPayment_url("https://rc-epay.esewa.com.np/api/epay/main/v2/form");

            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String createSigningString(EsewaRequestDto dto) {
        return "total_amount=" + dto.getTotalAmount()
                + ",transaction_uuid=" + dto.getTransactionUuid()
                + ",product_code=" + dto.getProductCode();
    }

    private String hmacSha256Base64(String data, String secret) throws Exception {
        Mac sha256_HMAC = Mac.getInstance(HMAC_SHA256);
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), HMAC_SHA256);
        sha256_HMAC.init(secret_key);
        byte[] hash = sha256_HMAC.doFinal(data.getBytes());
        return Base64.encodeBase64String(hash);
    }

}
