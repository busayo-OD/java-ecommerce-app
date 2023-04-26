package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.coupon.CouponDto;
import com.busayo.ecommercebackend.dto.coupon.CouponResponseDto;
import com.busayo.ecommercebackend.exception.CouponNotFoundException;
import com.busayo.ecommercebackend.model.Coupon;
import com.busayo.ecommercebackend.repository.CouponRepository;
import com.busayo.ecommercebackend.service.CouponService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {

    private CouponRepository couponRepository;

    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public Boolean addCoupon(CouponDto couponDto) {

        Coupon coupon = new Coupon();
        coupon.setCouponCode(couponDto.getCouponCode());
        coupon.setStatus("Active");
        coupon.setStartDate(couponDto.getStartDate());
        coupon.setEndDate(couponDto.getEndDate());
        coupon.setDiscountValue(couponDto.getDiscountValue());
        coupon.setCouponStatus(couponDto.getCouponStatus().toUpperCase());
        coupon.setType(couponDto.getType().toUpperCase());

        couponRepository.save(coupon);
        return true;
    }

    @Override
    public Coupon getCoupon(Long couponId) {
        Coupon coupon;
        coupon = couponRepository.findById(couponId)

                .orElseThrow(() -> new CouponNotFoundException(couponId));

        return coupon;
    }

    private List<Coupon> coupons = new ArrayList<>();

    @Override
    public List<Coupon> getAllCoupon() {
        coupons.clear();
        List<Coupon> allCoupons = couponRepository.findAll();

        for (Coupon coupon : allCoupons) {
            if ((allCoupons != null) && (coupon.getStatus().trim().equals("Active"))) {
                coupons.add(coupon);
            }
        }
        return coupons;
    }

    @Override
    public CouponResponseDto getCouponsWithPaginationAndSorting(String status, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Coupon> coupons = couponRepository.findByStatus(status, pageable);

        List<Coupon> couponList = coupons.getContent();

        List<CouponDto> content = couponList.stream().map(coupon -> mapToCouponDto(coupon)).collect(Collectors.toList());

        CouponResponseDto couponResponseDto = new CouponResponseDto();
        couponResponseDto.setContent(content);
        couponResponseDto.setPageNo(coupons.getNumber());
        couponResponseDto.setPageSize(coupons.getSize());
        couponResponseDto.setTotalElements(coupons.getTotalElements());
        couponResponseDto.setTotalPages(coupons.getTotalPages());
        couponResponseDto.setLast(coupons.isLast());

        return couponResponseDto;
    }

    @Override
    public Boolean updateCoupon(CouponDto couponDto, Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException(couponId));

        coupon.setCouponCode(couponDto.getCouponCode());
        coupon.setStartDate(couponDto.getStartDate());
        coupon.setEndDate(couponDto.getEndDate());
        coupon.setDiscountValue(couponDto.getDiscountValue());
        coupon.setCouponStatus(couponDto.getCouponStatus().toUpperCase());
        coupon.setType(couponDto.getType().toUpperCase());

        couponRepository.save(coupon);
        return true;
    }

    @Override
    public Boolean deleteCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException(couponId));

        coupon.setStatus("Deleted");
        couponRepository.save(coupon);
        return true;
    }

    private CouponDto mapToCouponDto(Coupon coupon){

        CouponDto couponDto = new CouponDto();
        couponDto.setId(coupon.getId());
        couponDto.setCouponCode(coupon.getCouponCode());
        couponDto.setCouponStatus(coupon.getCouponStatus());
        couponDto.setDiscountValue(coupon.getDiscountValue());
        couponDto.setType(coupon.getType());
        couponDto.setStartDate(coupon.getStartDate());
        couponDto.setEndDate(coupon.getEndDate());

        return couponDto;
    }
}