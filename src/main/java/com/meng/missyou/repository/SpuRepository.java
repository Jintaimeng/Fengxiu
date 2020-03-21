package com.meng.missyou.repository;

import com.meng.missyou.model.Spu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpuRepository extends JpaRepository<Spu, Long> {
    Spu getOneById(Long id);

    Page<Spu> findByCategoryIdOOrderByCategoryIdCategoryIdDesc(Long cid, Pageable pageable);
}
