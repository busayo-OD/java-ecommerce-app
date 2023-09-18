package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.shippingMethod.ShippingMethodDto;
import com.busayo.ecommercebackend.model.ShippingMethod;

import java.util.List;

public interface ShippingMethodService {
    ShippingMethod addShippingMethod(ShippingMethodDto shippingMethodDto);
    ShippingMethod getShippingMethod(Long shippingMethodId);
    List<ShippingMethod> getAllShippingMethods();
    boolean updateShippingMethod(ShippingMethodDto shippingMethodDto, Long shippingMethodId);
    boolean deleteShippingMethod(Long shippingMethodId);
}
