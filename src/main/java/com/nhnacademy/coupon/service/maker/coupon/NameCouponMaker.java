package com.nhnacademy.coupon.service.maker.coupon;

import com.nhnacademy.coupon.domain.coupon.BookIdCoupon;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
class NameCouponMaker implements CouponMaker {
    @Override
    public boolean match(CouponJpaEntity couponJpaEntity) {
        return couponJpaEntity.getBookId()!=null;
    }

    @Override
    public Coupon make(CouponJpaEntity entity) {
        return new BookIdCoupon(entity.getId(), entity.getName(),entity.getPolicyId(),entity.getQuantity(),entity.getIssueStartDate(),entity.getIssueEndDate(),entity.getBookId());
    }

    @Override
    public boolean match(Coupon coupon) {
        return BookIdCoupon.class.isAssignableFrom(coupon.getClass());
    }

    @Override
    public CouponJpaEntity make(Coupon coupon) {
        return new CouponJpaEntity(coupon);
    }
}
