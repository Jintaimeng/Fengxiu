package com.meng.missyou.repository;

import com.meng.missyou.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {//<BannerRepository操作的实体(返回的实体），实体主键的类型>

    Banner findOneById(Long id);

    Banner findOneByName(String name);
}
