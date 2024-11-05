package com.gbaraujosouza.api.service;

import com.gbaraujosouza.api.domain.coupon.Coupon;
import com.gbaraujosouza.api.domain.coupon.CouponRequestDTO;
import com.gbaraujosouza.api.domain.event.Event;
import com.gbaraujosouza.api.repositories.CouponRepository;
import com.gbaraujosouza.api.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private EventRepository eventRepository;


    public Coupon addCouponToEvent(UUID eventId, CouponRequestDTO couponRequestDTO) {
        Event findEvent = eventRepository.findById(eventId).orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Coupon coupon = new Coupon();
        coupon.setCode(couponRequestDTO.code());
        coupon.setValid(new Date(couponRequestDTO.valid()));
        coupon.setDiscount(couponRequestDTO.discount());
        coupon.setEvent(findEvent);

        couponRepository.save(coupon);
        return coupon;
    }
}
