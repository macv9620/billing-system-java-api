package com.macv.billing.service;

import com.macv.billing.persistence.entity.BrandEntity;
import com.macv.billing.persistence.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<BrandEntity> getAll(){
        return brandRepository.findAll();
    }
}
