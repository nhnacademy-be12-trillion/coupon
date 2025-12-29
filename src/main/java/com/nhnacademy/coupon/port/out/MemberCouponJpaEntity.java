package com.nhnacademy.coupon.port.out;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "member_coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@EntityListeners(AuditingEntityListener.class)
public class MemberCouponJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "coupon_id")
    private Long couponId;
    @Column(name = "is_use")
    private boolean isUse;
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    public MemberCouponJpaEntity(Long memberId, Long couponId) {
        this.memberId = memberId;
        this.couponId = couponId;
    }

    public void useCoupon() {
        isUse =true;
    }

    public void rollback() {
        isUse =false;
    }
}
