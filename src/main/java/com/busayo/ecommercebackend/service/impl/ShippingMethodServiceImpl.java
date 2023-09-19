package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.shippingMethod.ShippingMethodDto;
import com.busayo.ecommercebackend.exception.ShippingMethodDuplicateException;
import com.busayo.ecommercebackend.exception.ShippingMethodNotFoundException;
import com.busayo.ecommercebackend.model.ShippingMethod;
import com.busayo.ecommercebackend.repository.ShippingMethodRepository;
import com.busayo.ecommercebackend.service.ShippingMethodService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingMethodServiceImpl implements ShippingMethodService {

    private final ShippingMethodRepository shippingMethodRepository;

    public ShippingMethodServiceImpl(ShippingMethodRepository shippingMethodRepository) {
        this.shippingMethodRepository = shippingMethodRepository;
    }

    @Override
    public ShippingMethod addShippingMethod(ShippingMethodDto shippingMethodDto) {
        boolean existingName = shippingMethodRepository.existsByName(shippingMethodDto.getName());

        if (existingName) {
            throw new ShippingMethodDuplicateException();
        }
        ShippingMethod shippingMethod = new ShippingMethod();

        shippingMethod.setName(shippingMethodDto.getName().toUpperCase());
        shippingMethod.setStatus("Active");
        shippingMethod.setDescription(shippingMethodDto.getDescription());
        shippingMethod.setTimeRange(shippingMethodDto.getTimeRange());
        shippingMethod.setPrice(shippingMethodDto.getPrice());
        shippingMethodRepository.save(shippingMethod);
        return shippingMethod;
    }

    @Override
    public ShippingMethod getShippingMethod(Long shippingMethodId) {
        ShippingMethod shippingMethod;
        shippingMethod = shippingMethodRepository.findById(shippingMethodId)
                .orElseThrow(() -> new ShippingMethodNotFoundException(shippingMethodId));
        return shippingMethod;
    }

    @Override
    public List<ShippingMethod> getAllShippingMethods() {
        return shippingMethodRepository.findAllByStatusIgnoreCase("Active");
    }


    @Override
    public boolean updateShippingMethod(ShippingMethodDto shippingMethodDto, Long shippingMethodId) {

        ShippingMethod shippingMethod = shippingMethodRepository.findById(shippingMethodId)
                .orElseThrow(() -> new ShippingMethodNotFoundException(shippingMethodId));
        if (!shippingMethodDto.getName().isEmpty()) {
            shippingMethod.setName(shippingMethodDto.getName());
        }

        if (!shippingMethodDto.getDescription().isEmpty()) {
            shippingMethod.setDescription(shippingMethodDto.getDescription());
        }

        if (!shippingMethodDto.getTimeRange().isEmpty()) {
            shippingMethod.setTimeRange(shippingMethodDto.getTimeRange());
        }

        if (!(shippingMethodDto.getPrice() == 0.0)) {
            shippingMethod.setPrice(shippingMethodDto.getPrice());
        }

        shippingMethodRepository.save(shippingMethod);
        return true;
    }

    @Override
    public boolean deleteShippingMethod(Long shippingMethodId) {
        ShippingMethod shippingMethod = shippingMethodRepository.findById(shippingMethodId)
                .orElseThrow(() -> new ShippingMethodNotFoundException(shippingMethodId));

        shippingMethod.setStatus("Deleted");
        shippingMethodRepository.save(shippingMethod);
        return true;
    }
}

