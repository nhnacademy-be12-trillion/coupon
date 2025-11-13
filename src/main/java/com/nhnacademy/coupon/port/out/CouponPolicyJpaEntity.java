package com.nhnacademy.coupon.port.out;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon_policy")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
public class CouponPolicyJpaEntity {
    @Id
    private Long id;
    private Double discountValue;
    private Long minOrderPrice;
    private Long maxDiscountPrice;
    private CouponDiscountTypeColumn discountType;
}
