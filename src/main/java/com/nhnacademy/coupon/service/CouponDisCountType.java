package com.nhnacademy.coupon.service;

import com.nhnacademy.coupon.port.out.CouponDiscountTypeColumn;

public enum CouponDisCountType {
    FIXED_AMOUNT,
    RATE;
    static CouponDisCountType getCouponDisCountType(CouponDiscountTypeColumn column) {
        return switch(column) {
            case CouponDiscountTypeColumn.FIX_AMOUNT-> CouponDisCountType.FIXED_AMOUNT;
            case CouponDiscountTypeColumn.RATE-> CouponDisCountType.RATE;
        };
    }
}
