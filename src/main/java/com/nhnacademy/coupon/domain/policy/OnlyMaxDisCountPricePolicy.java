package com.nhnacademy.coupon.domain.policy;

public class OnlyMaxDisCountPricePolicy extends CouponPolicy{
    public OnlyMaxDisCountPricePolicy(Long id, Long minOrderPrice, Long maxDiscountPrice, Double discountValue,
                                      CouponDisCountType couponDiscountType) {
        super(id, minOrderPrice, maxDiscountPrice, discountValue, couponDiscountType);
    }

    @Override
    public boolean match() {
        return getMinOrderPrice()==null&&getMaxDiscountPrice()!=null;
    }

    @Override
    protected Price getSalePrice(Price price) {
        Price salePrice = getCouponDiscountType().getSalePrice(getDiscountValue(), price);
        if(salePrice.value()<getMaxDiscountPrice()){
            return salePrice;
        }
        return new Price(getMaxDiscountPrice());
    }
}
