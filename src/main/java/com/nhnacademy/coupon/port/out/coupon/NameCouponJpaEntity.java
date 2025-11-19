package com.nhnacademy.coupon.port.out.coupon;

import com.nhnacademy.coupon.domain.Book;
import com.nhnacademy.coupon.domain.coupon.NameCoupon;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "NameCoupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NameCouponJpaEntity extends CouponJpaEntity {
    String bookName;
    public NameCouponJpaEntity(NameCoupon coupon) {
        super(coupon);
        this.bookName = coupon.getBookName();
    }
    @Override
    public CouponKindColumn getCouponType(){
        return CouponKindColumn.NAME;
    }
    @Override
    public boolean isAvailable(LocalDateTime now, Book book) {
        return super.isAvailable(now, book)&&bookName.equals(book.name());
    }
}
