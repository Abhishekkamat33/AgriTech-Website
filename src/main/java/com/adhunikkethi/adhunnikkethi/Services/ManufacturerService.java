package com.adhunikkethi.adhunnikkethi.Services;

import com.adhunikkethi.adhunnikkethi.entities.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {
    List<Manufacturer> getAllManufacturers();
    Optional<Manufacturer> getManufacturerById(Long id);
    Manufacturer createManufacturer(Manufacturer manufacturer);
    Optional<Manufacturer> updateManufacturer(Long id, Manufacturer manufacturer);
    boolean deleteManufacturer(Long id);
}
