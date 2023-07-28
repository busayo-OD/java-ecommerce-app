package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.coupon.CouponDto;
import com.busayo.ecommercebackend.dto.coupon.CouponResponseDto;
import com.busayo.ecommercebackend.model.Coupon;
import com.busayo.ecommercebackend.service.CouponService;
import com.busayo.ecommercebackend.utils.AppConstants2;
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

    @GetMapping("/pagination/{status}")
    public CouponResponseDto getCouponsWithPaginationAndSorting(
            @PathVariable String status,
            @RequestParam(value = "pageNo", defaultValue = AppConstants2.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants2.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants2.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants2.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return couponService.getCouponsWithPaginationAndSorting(status, pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/status/{coupon-status}/{status}")
    public CouponResponseDto getCouponsByStatus(
            @PathVariable("coupon-status") String couponStatus,
            @PathVariable String status,
            @RequestParam(value = "pageNo", defaultValue = AppConstants2.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants2.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants2.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants2.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return couponService.getCouponsByStatus(couponStatus, status, pageNo, pageSize, sortBy, sortDir);
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
