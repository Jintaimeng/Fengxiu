package com.meng.missyou.service;

import com.meng.missyou.model.Spu;
import com.meng.missyou.repository.SpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpuService {
    @Autowired
    SpuRepository spuRepository;

    public Spu getSpu(Long id) {
        return this.spuRepository.getOneById(id);
    }

    public List<Spu> getLatestPagingSpu() {
        return this.spuRepository.findAll();
    }
}
