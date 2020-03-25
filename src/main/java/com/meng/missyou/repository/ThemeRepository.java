package com.meng.missyou.repository;

import com.meng.missyou.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    //SQL  JPQL:Java Persistence Query Language
    @Query("select t from Theme t where t.name in (:names)")
    List<Theme> findByNames(List<String> names);

    Optional<Theme> findByName(String name);

}
