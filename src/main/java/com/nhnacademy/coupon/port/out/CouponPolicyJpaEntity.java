package com.nhnacademy.coupon.port.out;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CouponPolicy")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Inheritance
public class CouponPolicyJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double discountValue;
    private Long minOrderPrice;
    private Long maxDiscountPrice;
    private CouponDiscountTypeColumn discountType;

    public CouponPolicyJpaEntity(Double discountValue, Long minOrderPrice, Long maxDiscountPrice,
                                 CouponDiscountTypeColumn discountType) {
        this.discountValue = discountValue;
        this.minOrderPrice = minOrderPrice;
        this.maxDiscountPrice = maxDiscountPrice;
        this.discountType = discountType;
    }
}
