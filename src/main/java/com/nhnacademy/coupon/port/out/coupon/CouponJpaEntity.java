package com.nhnacademy.coupon.port.out.coupon;

import com.nhnacademy.coupon.domain.coupon.CategoryCoupon;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.domain.coupon.NameCoupon;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Long policyId;
    private Long quantity;
    private LocalDateTime issueStartDate;
    private LocalDateTime issueEndDate;
    private String categoryName;
    private String bookName;

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
        if(coupon instanceof NameCoupon nameCoupon) {
            this.bookName = nameCoupon.getBookName();
        }
        if(coupon instanceof CategoryCoupon categoryCoupon) {
            this.categoryName = categoryCoupon.getCategoryName();
        }
    }
}
