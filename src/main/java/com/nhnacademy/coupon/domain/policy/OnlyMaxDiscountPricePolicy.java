package com.nhnacademy.coupon.domain.policy;

public class OnlyMaxDiscountPricePolicy extends CouponPolicy{
    public OnlyMaxDiscountPricePolicy(Long id, String name, Long minOrderPrice, Long maxDiscountPrice, Double discountValue,
                                      CouponDiscountType couponDiscountType) {
        super(id, name,minOrderPrice, maxDiscountPrice, discountValue, couponDiscountType);
    }

    @Override
    public boolean match() {
        return getMinOrderPrice()==null&&getMaxDiscountPrice()!=null;
    }

    @Override
    protected Price getDiscountAmount(Price price) {
        Price salePrice = getCouponDiscountType().getDiscountAmount(getDiscountValue(), price);
        if(salePrice.value()<getMaxDiscountPrice()){
            return salePrice;
        }
        return new Price(getMaxDiscountPrice());
    }
}
