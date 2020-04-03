package com.meng.missyou.repository;

import com.meng.missyou.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query("select c from Coupon c\\n\" +\n" +
            "            \"join c.categoryList ca\\n\" +\n" +
            "            \"join Activity a on a.id = c.activityId\\n\" +\n" +
            "            \"where ca.id = :cid\\n\" +\n" +
            "            \"and a.startTime < :now\\n\" +\n" +
            "            \"and a.endTime > :now\\n")
    List<Coupon> findByCategory(Long cid, Date now);

    @Query("select c from Coupon c\\n\" +\n" +
            "            \"join Activity a on c.activityId = a.id\\n\" +\n" +
            "            \"where c.wholeStore = :isWholeStore\\n\" +\n" +
            "            \"and a.startTime < :now\\n\" +\n" +
            "            \"and a.endTime > :now\\n")
    List<Coupon> findByWholeStore(Boolean isWholeStore, Date now);

    @Query("select c from Coupon c\n" +
            "join UserCoupon uc\n" +
            "on c.id = uc.couponId\n" +
            "join User u\n" +
            "on u.id = uc.userId\n" +
            "where uc.status = 1 \n" +
            "and u.id = :uid\n" +
            "and c.startTime < :now\n" +
            "and c.endTime > :now\n" +
            "and uc.orderId is null")
    List<Coupon> findMyAvailable(Long uid, Date now);

    @Query("select c From Coupon c\n" +
            "join UserCoupon uc\n" +
            "on c.id = uc.couponId\n" +
            "join User u\n" +
            "on u.id = uc.userId\n" +
            "where u.id = :uid\n" +
            "and uc.status = 2\n" +
            "and uc.orderId is not null \n" +
            "and c.startTime < :now\n" +
            "and c.endTime > :now")
    List<Coupon> findMyUsed(@Param("uid") Long uid, @Param("now") Date now);

    @Query("select c From Coupon c\n" +
            "join UserCoupon uc\n" +
            "on c.id = uc.couponId\n" +
            "join User u\n" +
            "on u.id = uc.userId\n" +
            "where u.id = :uid\n" +
            "and uc.orderId is null\n" +
            "and uc.status <> 2\n" +
            "and c.endTime < :now")
    List<Coupon> findMyExpired(Long uid, Date now);
}
