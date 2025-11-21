package com.nhnacademy.coupon.domain.policy;

import com.nhnacademy.coupon.error.CustomException;

public class AllPricePolicy extends CouponPolicy{
    public AllPricePolicy(Long id, String name,Long minOrderPrice, Long maxDiscountPrice, Double discountValue,
                          CouponDisCountType couponDiscountType) {
        super(id, name,minOrderPrice, maxDiscountPrice, discountValue, couponDiscountType);
    }

    @Override
    public boolean match() {
        return getMinOrderPrice()!=null&&getMaxDiscountPrice()!=null;
    }

    @Override
    protected Price getSalePrice(Price price) {
        if(price.value()<getMinOrderPrice()) {
            throw new CustomException("error.message.minOrderPrice",new Object[]{price.value(),getMinOrderPrice()});
        }

        Price salePrice = getCouponDiscountType().getSalePrice(getDiscountValue(), price);
        if(salePrice.value()<getMaxDiscountPrice()){
            return salePrice;
        }
        return new Price(getMaxDiscountPrice());
    }

}
