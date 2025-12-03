package com.nhnacademy.coupon.domain.policy;

import com.nhnacademy.coupon.error.CustomException;

public class OnlyMinOrderPriceCouponPolicy extends CouponPolicy{
    public OnlyMinOrderPriceCouponPolicy(Long id, String name,Long minOrderPrice, Long maxDiscountPrice, Double discountValue,
                                         CouponDiscountType couponDiscountType) {
        super(id, name,minOrderPrice, maxDiscountPrice, discountValue, couponDiscountType);
    }

    @Override
    public boolean match() {
        return getMinOrderPrice()!=null&&getMaxDiscountPrice()==null;
    }

    @Override
    protected Price getSalePrice(Price price) {
        if(price.value()<getMinOrderPrice()) {
            throw new CustomException("error.message.minOrderPrice",new Object[]{price.value(),getMinOrderPrice()});
        }

        return getCouponDiscountType().getSalePrice(getDiscountValue(), price);
    }
}
