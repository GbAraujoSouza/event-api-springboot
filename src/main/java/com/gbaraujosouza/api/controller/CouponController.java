package com.gbaraujosouza.api.controller;

import com.gbaraujosouza.api.domain.coupon.Coupon;
import com.gbaraujosouza.api.domain.coupon.CouponRequestDTO;
import com.gbaraujosouza.api.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {
    @Autowired
    CouponService couponService;

    @PostMapping("/event/{eventId}")
    public ResponseEntity<Coupon> create(@PathVariable UUID eventId, @RequestBody CouponRequestDTO couponRequestDTO) {
        Coupon newCoupon = this.couponService.addCouponToEvent(eventId, couponRequestDTO);
        return ResponseEntity.ok(newCoupon);
    }
}
