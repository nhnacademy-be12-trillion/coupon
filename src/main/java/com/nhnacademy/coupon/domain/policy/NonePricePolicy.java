package com.nhnacademy.coupon.domain.policy;

public class NonePricePolicy extends CouponPolicy {
    public NonePricePolicy(Long id, String name,Long minOrderPrice, Long maxDiscountPrice, Double discountValue,
                           CouponDiscountType couponDiscountType) {
        super(id, name,minOrderPrice, maxDiscountPrice, discountValue, couponDiscountType);
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
