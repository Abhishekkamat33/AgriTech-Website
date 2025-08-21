package com.adhunikkethi.adhunnikkethi.Services;

import com.adhunikkethi.adhunnikkethi.Respository.ManufacturerRepository;
import com.adhunikkethi.adhunnikkethi.entities.Manufacturer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    @Override
    public Optional<Manufacturer> getManufacturerById(Long id) {
        return manufacturerRepository.findById(id);
    }

    @Override
    public Manufacturer createManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public Optional<Manufacturer> updateManufacturer(Long id, Manufacturer manufacturerDetails) {
        return manufacturerRepository.findById(id).map(manufacturer -> {
            manufacturer.setName(manufacturerDetails.getName());
            manufacturer.setContactInfo(manufacturerDetails.getContactInfo());
            manufacturer.setDescription(manufacturerDetails.getDescription());
            manufacturer.setStatus(manufacturerDetails.getStatus());
            return manufacturerRepository.save(manufacturer);
        });
    }

    @Override
    public boolean deleteManufacturer(Long id) {
        if (manufacturerRepository.existsById(id)) {
            manufacturerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
