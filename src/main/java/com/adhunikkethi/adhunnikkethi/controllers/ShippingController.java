package com.adhunikkethi.adhunnikkethi.controllers;

import com.adhunikkethi.adhunnikkethi.Dto.ShippingDto;
import com.adhunikkethi.adhunnikkethi.Services.ShippingService;
import com.adhunikkethi.adhunnikkethi.entities.Shipping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/shippings")
public class ShippingController {

    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<ShippingDto> getAllShippings() {
        List<Shipping> shippings = shippingService.getAllShippings();
        List<ShippingDto> shippingDtos = new ArrayList<>();
        for (Shipping shipping : shippings) {
            ShippingDto dto = new ShippingDto();
            dto.setId(shipping.getShippingId());
            dto.setFirstName(shipping.getFirstName());
            dto.setLastName(shipping.getLastName());
            dto.setAddress(shipping.getAddress());
            dto.setCity(shipping.getCity());
            dto.setCountry(shipping.getCountry());
            dto.setPhone(shipping.getPhone());
            dto.setShippingDate(shipping.getShippingDate());
            dto.setShippingStatus(String.valueOf(shipping.getShippingStatus()));
            dto.setState(shipping.getState());
            dto.setZipCode(shipping.getZipCode());
            shippingDtos.add(dto);
        }
        return shippingDtos;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Shipping> getShippingById(@PathVariable Long id) {
        return shippingService.getShippingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Shipping> createShipping(@RequestBody ShippingDto shippingDto) {
        Shipping createdShipping = shippingService.createShipping(shippingDto);
        return new ResponseEntity<>(createdShipping, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shipping> updateShipping(@PathVariable Long id, @RequestBody ShippingDto shippingDto) {
        return shippingService.updateShipping(id, shippingDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipping(@PathVariable Long id) {
        boolean deleted = shippingService.deleteShipping(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
