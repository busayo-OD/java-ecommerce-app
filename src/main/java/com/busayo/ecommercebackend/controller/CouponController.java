package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.coupon.CouponDto;
import com.busayo.ecommercebackend.model.Coupon;
import com.busayo.ecommercebackend.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Boolean addCoupon(@RequestBody CouponDto couponDto){
        return couponService.addCoupon(couponDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<Coupon> getCoupon(@PathVariable("id") Long couponId){
        Coupon coupon = couponService.getCoupon(couponId);
        return  ResponseEntity.ok(coupon);
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons(){
        return ResponseEntity.ok(couponService.getAllCoupon());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<Boolean> updateCoupon(@RequestBody CouponDto couponDto,
                                                     @PathVariable("id") Long couponId){
        return ResponseEntity.ok(couponService.updateCoupon(couponDto, couponId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteCoupon(@PathVariable Long id) {

        return ResponseEntity.ok(couponService.deleteCoupon(id));
    }

}
