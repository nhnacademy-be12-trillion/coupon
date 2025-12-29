package com.nhnacademy.coupon.port.out;

import com.nhnacademy.coupon.domain.policy.CouponDiscountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String name;
    @Column(name = "discount_value")
    private Double discountValue;
    @Column(name = "min_order_price")
    private Long minOrderPrice;
    @Column(name = "max_discount_price")
    private Long maxDiscountPrice;
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    private CouponDiscountType discountType;

    public CouponPolicyJpaEntity(String name, Double discountValue, Long minOrderPrice, Long maxDiscountPrice,
                                 CouponDiscountType discountType) {
        this.name = name;
        this.discountValue = discountValue;
        this.minOrderPrice = minOrderPrice;
        this.maxDiscountPrice = maxDiscountPrice;
        this.discountType = discountType;
    }
}
