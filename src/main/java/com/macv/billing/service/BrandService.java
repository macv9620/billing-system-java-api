package com.macv.billing.service;

import com.macv.billing.persistence.entity.BrandEntity;
import com.macv.billing.persistence.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<BrandEntity> getAll(){
        return brandRepository.findAll();
    }
}
