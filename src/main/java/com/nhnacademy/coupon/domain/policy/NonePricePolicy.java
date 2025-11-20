package com.nhnacademy.coupon.domain.policy;

public class NonePricePolicy extends CouponPolicy {
    public NonePricePolicy(Long id, Long minOrderPrice, Long maxDiscountPrice, Double discountValue,
                           CouponDisCountType couponDiscountType) {
        super(id, minOrderPrice, maxDiscountPrice, discountValue, couponDiscountType);
    }

    @Override
    public boolean match() {
        return getMinOrderPrice()==null&&getMaxDiscountPrice()==null;
    }

    @Override
    protected Price getSalePrice(Price price) {
        return getCouponDiscountType().getSalePrice(getDiscountValue(), price);
    }
}
