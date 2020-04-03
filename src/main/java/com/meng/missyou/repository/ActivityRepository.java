package com.meng.missyou.repository;

import com.meng.missyou.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Activity findByName(String name);

    Optional<Activity> findByCouponListId(Long couponid);
}
