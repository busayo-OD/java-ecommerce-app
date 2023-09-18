package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, Long> {
    boolean existsByName(String name);
    List<ShippingMethod> findAllByStatusIgnoreCase(String status);
}
