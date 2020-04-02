package com.meng.missyou.repository;

import com.meng.missyou.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    List<Coupon> findByCategory(Long cid, Date date);
}
