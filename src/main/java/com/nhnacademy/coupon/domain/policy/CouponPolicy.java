package com.nhnacademy.coupon.domain.policy;

import com.nhnacademy.coupon.error.CustomException;
import lombok.Getter;

@Getter
public abstract class CouponPolicy {
    private Long id;
    private Long minOrderPrice;
    private Long maxDiscountPrice;
    private Double discountValue;
    private CouponDisCountType couponDiscountType;

    protected CouponPolicy(Long id,Long minOrderPrice, Long maxDiscountPrice, Double discountValue, CouponDisCountType couponDiscountType) {
        if(discountValue==null||discountValue<=0){
            throw new CustomException("error.message.discountValueMustBePositive",new Object[]{discountValue});
        }

        this.id = id;
        this.minOrderPrice = minOrderPrice;
        this.maxDiscountPrice = maxDiscountPrice;
        this.discountValue = discountValue;
        this.couponDiscountType = couponDiscountType;
    }

    public abstract boolean match();

    public Price getApplyCouponPrice(Price price){
        return new Price(price.value()-getSalePrice(price).value());
    }
    protected abstract Price getSalePrice(Price price);
}