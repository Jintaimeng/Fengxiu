package com.meng.missyou.repository;

import com.meng.missyou.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkuRepository extends JpaRepository<Sku, Long> {
    List<Sku> findAllByIdIn(List<Long> ids);

    @Modifying  //@Query通常指定查询语句，更新或插入语句要加上@Modifying
    @Query("update Sku s \\n\" +\n" +
            "            \"set s.stock = s.stock - :quantity\\n\" +\n" +
            "            \"where s.id = :sid\\n\" +\n" +
            "            \"and s.stock >= :quantity")
    int reduceStock(Long sid, Long quantity);
}
