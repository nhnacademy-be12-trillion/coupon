package com.nhnacademy.coupon.port.out.coupon;

import com.nhnacademy.coupon.domain.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
public class CouponJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Long policyId;
    private Long quantity;
    private LocalDateTime issueStartDate;
    private LocalDateTime issueEndDate;

    public CouponKindColumn getCouponType(){
        return CouponKindColumn.DEFAULT;
    }
    public boolean isAvailable(LocalDateTime now, Book book) {
        return issueStartDate.isAfter(now) && now.isBefore(issueEndDate)&&quantity>0;
    }

}
