package com.nhnacademy.coupon.port.out.coupon;

import com.nhnacademy.coupon.domain.coupon.BookIdCoupon;
import com.nhnacademy.coupon.domain.coupon.CategoryIdCoupon;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import jakarta.persistence.Column;
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
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CouponJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "policy_id")
    private Long policyId;
    @Column(name = "quantity")
    private Long quantity;
    @Column(name = "issue_start_date")
    private LocalDateTime issueStartDate;
    @Column(name = "issue_end_date")
    private LocalDateTime issueEndDate;
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "book_id")
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
