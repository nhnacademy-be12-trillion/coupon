package com.nhnacademy.coupon.port.out.coupon;

import com.nhnacademy.coupon.domain.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "name_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NameCouponJpaEntity extends CouponJpaEntity {
    String bookName;

    public CouponKindColumn getCouponType(){
        return CouponKindColumn.NAME;
    }
    public boolean isAvailable(LocalDateTime now, Book book) {
        return super.isAvailable(now, book)&&bookName.equals(book.name());
    }
}
