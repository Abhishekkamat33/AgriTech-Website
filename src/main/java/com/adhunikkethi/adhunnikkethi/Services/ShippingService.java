package com.adhunikkethi.adhunnikkethi.Services;

import com.adhunikkethi.adhunnikkethi.Dto.ShippingDto;
import com.adhunikkethi.adhunnikkethi.entities.Shipping;

import java.util.List;
import java.util.Optional;

public interface ShippingService {
    List<Shipping> getAllShippings();
    Optional<Shipping> getShippingById(Long id);
    Shipping createShipping(ShippingDto shippingDto);
    Optional<Shipping> updateShipping(Long id, ShippingDto shippingDto);
    boolean deleteShipping(Long id);
}
