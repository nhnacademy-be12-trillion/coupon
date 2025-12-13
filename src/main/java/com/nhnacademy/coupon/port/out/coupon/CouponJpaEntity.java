package com.nhnacademy.coupon.port.out.coupon;

import com.nhnacademy.coupon.domain.coupon.BookIdCoupon;
import com.nhnacademy.coupon.domain.coupon.CategoryIdCoupon;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CouponJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long policyId;
    private Long quantity;
    private LocalDateTime issueStartDate;
    private LocalDateTime issueEndDate;
    private Long categoryId;
    private Long bookId;

    public CouponJpaEntity(Coupon coupon) {
        this.id = coupon.getId();
        this.name = coupon.getName();
        this.policyId = coupon.getPolicyId();
        this.quantity = coupon.getQuantity();
        this.issueStartDate = coupon.getIssueStartDate();
        this.issueEndDate = coupon.getIssueEndDate();
        setValue(coupon);
    }
    private void setValue(Coupon coupon) {
        if(coupon instanceof BookIdCoupon bookIdCoupon) {
            this.bookId = bookIdCoupon.getBookId();
        }
        if(coupon instanceof CategoryIdCoupon categoryIdCoupon) {
            this.categoryId = categoryIdCoupon.getCategoryId();
        }
    }
}
