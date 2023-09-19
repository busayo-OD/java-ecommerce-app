package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
}
