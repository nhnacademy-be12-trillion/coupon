package com.nhnacademy.coupon.port.out.coupon;

import com.nhnacademy.coupon.domain.Book;
import com.nhnacademy.coupon.service.coupon.CategoryCoupon;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CategoryCouponJpaEntity extends CouponJpaEntity {
    private String categoryName;

    public CategoryCouponJpaEntity(CategoryCoupon coupon) {
        super(coupon);
        this.categoryName=coupon.getCategoryName();
    }
    @Override
    public CouponKindColumn getCouponType() {
        return CouponKindColumn.CATEGORY;
    }

    @Override
    public boolean isAvailable(LocalDateTime now, Book book) {
        return super.isAvailable(now,book)&&categoryName.equals(book.category());
    }
}
