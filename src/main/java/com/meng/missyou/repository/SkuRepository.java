package com.meng.missyou.repository;

import com.meng.missyou.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkuRepository extends JpaRepository<Sku, Long> {
    List<Sku> findAllByIdIn(List<Long> ids);

    @Modifying  //@Query通常指定查询语句，更新或插入语句要加上@Modifying
    @Query("update Sku s \n" +
            "set s.stock = s.stock - :quantity\n" +
            "where s.id = :sid\n" +
            "and s.stock >= :quantity")
    int reduceStock(Long sid, Long quantity);

    @Modifying
    @Query("update Sku s set s.stock=s.stock+(:quantity) where s.id = :sid")
    int recoverStock(@Param(value = "sid") Long sid, @Param(value = "quantity") Long quantity);
}
