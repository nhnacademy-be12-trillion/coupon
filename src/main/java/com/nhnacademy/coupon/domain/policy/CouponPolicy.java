package com.nhnacademy.coupon.domain.policy;

import com.nhnacademy.coupon.error.CustomException;
import lombok.Getter;

@Getter
public abstract class CouponPolicy {
    private Long id;
    private String name;
    private Long minOrderPrice;
    private Long maxDiscountPrice;
    private Double discountValue;
    private CouponDiscountType couponDiscountType;

    protected CouponPolicy(Long id,String name,Long minOrderPrice, Long maxDiscountPrice, Double discountValue, CouponDiscountType couponDiscountType) {
        if(discountValue==null||discountValue<=0){
            throw new CustomException("error.message.discountValueMustBePositive",new Object[]{discountValue});
        }

        this.id = id;
        this.name = name;
        this.minOrderPrice = minOrderPrice;
        this.maxDiscountPrice = maxDiscountPrice;
        this.discountValue = discountValue;
        this.couponDiscountType = couponDiscountType;
    }

    public abstract boolean match();

    public Price getApplyCouponPrice(Price price){
        return new Price(price.value()- getDiscountAmount(price).value());
    }
    protected abstract Price getDiscountAmount(Price price);
}