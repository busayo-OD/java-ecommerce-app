package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.shippingMethod.ShippingMethodDto;
import com.busayo.ecommercebackend.model.ShippingMethod;
import com.busayo.ecommercebackend.service.ShippingMethodService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipping-methods")
public class ShippingMethodController {

    private final ShippingMethodService shippingMethodService;

    public ShippingMethodController(ShippingMethodService shippingMethodService) {
        this.shippingMethodService = shippingMethodService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void addShippingMethod (@RequestBody ShippingMethodDto shippingMethodDto){
        shippingMethodService.addShippingMethod(shippingMethodDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<ShippingMethod> getShippingMethod(@PathVariable("id") Long shippingMethodId){
        ShippingMethod shippingMethod = shippingMethodService.getShippingMethod(shippingMethodId);
        return  ResponseEntity.ok(shippingMethod);
    }

    @GetMapping
    public ResponseEntity<List<ShippingMethod>> getAllShippingMethod(){
        return ResponseEntity.ok(shippingMethodService.getAllShippingMethods());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public boolean updateShippingMethod(@RequestBody ShippingMethodDto shippingMethodDto,
                                        @PathVariable("id") Long shippingMethodId){
        return shippingMethodService.updateShippingMethod(shippingMethodDto, shippingMethodId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public void deleteShippingMethod(@PathVariable Long id) {
        shippingMethodService.deleteShippingMethod(id);
    }
}

