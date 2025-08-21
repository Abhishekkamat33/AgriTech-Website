package com.adhunikkethi.adhunnikkethi.Services;

import com.adhunikkethi.adhunnikkethi.Dto.ShippingDto;
import com.adhunikkethi.adhunnikkethi.Dto.OrderDetailsDto;
import com.adhunikkethi.adhunnikkethi.Respository.ShippingRepository;
import com.adhunikkethi.adhunnikkethi.Respository.UserRepository;
import com.adhunikkethi.adhunnikkethi.entities.Shipping;
import com.adhunikkethi.adhunnikkethi.entities.User;
import com.adhunikkethi.adhunnikkethi.entities.OrderDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShippingServiceImpl implements ShippingService {

    private final ShippingRepository shippingRepository;
    private final UserRepository userRepository;

    public ShippingServiceImpl(ShippingRepository shippingRepository,
                               UserRepository userRepository) {
        this.shippingRepository = shippingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Shipping> getAllShippings() {
        return shippingRepository.findAll();
    }

    @Override
    public Optional<Shipping> getShippingById(Long id) {
        return shippingRepository.findById(id);
    }

    @Override
    @Transactional
    public Shipping createShipping(ShippingDto shippingDto) {

        if (shippingDto.getShippingDate() == null) {
            shippingDto.setShippingDate(LocalDateTime.now());
        }
        Long userId = shippingDto.getUserId();
        if (userId == null) {
            throw new IllegalArgumentException("UserId must not be null");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Shipping shipping = new Shipping();
        shipping.setUser(user);
        shipping.setAddress(shippingDto.getAddress());
        shipping.setFirstName(shippingDto.getFirstName());
        shipping.setLastName(shippingDto.getLastName());
        shipping.setEmail(shippingDto.getEmail());
        shipping.setState(shippingDto.getState());
        shipping.setCity(shippingDto.getCity());
        shipping.setCountry(shippingDto.getCountry());
        shipping.setZipCode(shippingDto.getZipCode());
        shipping.setPhone(shippingDto.getPhone());
        shipping.setShippingDate(shippingDto.getShippingDate());
        shipping.setShippingStatus(Shipping.ShippingStatus.valueOf(shippingDto.getShippingStatus()));
        // Map order details DTO to entity if needed
        return shippingRepository.save(shipping);
    }

    @Override
    @Transactional
    public Optional<Shipping> updateShipping(Long id, ShippingDto shippingDto) {
        return shippingRepository.findById(id).map(shipping -> {
            shipping.setAddress(shippingDto.getAddress());
            shipping.setCity(shippingDto.getCity());
            shipping.setZipCode(shippingDto.getZipCode());
            shipping.setCountry(shippingDto.getCountry());
            shipping.setPhone(shippingDto.getPhone());
            shipping.setShippingStatus(Shipping.ShippingStatus.valueOf(shippingDto.getShippingStatus()));
            shipping.setShippingDate(shippingDto.getShippingDate());

            Long userId = shippingDto.getUserId();
            if (userId == null) {
                throw new IllegalArgumentException("UserId must not be null");
            }
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
            shipping.setUser(user);

            // Update order details if needed (not shown for brevity)

            return shippingRepository.save(shipping);
        });
    }

    @Override
    public boolean deleteShipping(Long id) {
        if (shippingRepository.existsById(id)) {
            shippingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private OrderDetails convertOrderDetailDtoToEntity(OrderDetailsDto dto) {
        OrderDetails entity = new OrderDetails();
        // You might want to fetch Product entity by dto.getProductId() before setting
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());
        // Set product entity as needed
        return entity;
    }
}
