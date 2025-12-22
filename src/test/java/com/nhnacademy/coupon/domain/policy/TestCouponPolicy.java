package com.nhnacademy.coupon.domain.policy;

public class TestCouponPolicy extends CouponPolicy {
    public static final Long MIN_ORDER_PRICE=10_000L;
    public static final Long  MAX_ORDER_PRICE=100_000L;
    public static final Double DISOUNT=10_000D;

    public TestCouponPolicy() {
        super(null, null,MIN_ORDER_PRICE, MAX_ORDER_PRICE, DISOUNT, CouponDiscountType.FIXED_AMOUNT);
    }

    protected TestCouponPolicy(Long id, String name,Long minOrderPrice, Long maxDiscountPrice, Double discountValue,
                               CouponDiscountType couponDiscountType) {
        super(id, name,minOrderPrice, maxDiscountPrice, discountValue, couponDiscountType);
    }

    @Override
    public boolean match() {
        return false;
    }

    @Override
    public Price getDiscountAmount(Price price) {
        return new AllPricePolicy(getId(),getName(),getMinOrderPrice(),getMaxDiscountPrice(),getDiscountValue(),getCouponDiscountType()).getDiscountAmount(price);
    }
}