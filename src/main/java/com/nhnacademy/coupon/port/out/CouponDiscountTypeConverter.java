package com.nhnacademy.coupon.port.out;

import com.nhnacademy.coupon.domain.policy.CouponDisCountType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CouponDiscountTypeConverter implements AttributeConverter<CouponDiscountTypeColumn, CouponDisCountType> {

    @Override
    public CouponDisCountType convertToDatabaseColumn(CouponDiscountTypeColumn attribute) {
        return switch(attribute) {
            case CouponDiscountTypeColumn.FIX_AMOUNT-> CouponDisCountType.FIXED_AMOUNT;
            case CouponDiscountTypeColumn.RATE-> CouponDisCountType.RATE;
        };
    }

    @Override
    public CouponDiscountTypeColumn convertToEntityAttribute(CouponDisCountType dbData) {
        return switch (dbData){
            case FIXED_AMOUNT -> CouponDiscountTypeColumn.FIX_AMOUNT;
            case RATE -> CouponDiscountTypeColumn.RATE;
        };
    }
}
