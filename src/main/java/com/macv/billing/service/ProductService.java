package com.macv.billing.service;

import com.macv.billing.persistence.entity.ProductEntity;
import com.macv.billing.persistence.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductEntity> getAll(){
        return productRepository.findAll();
    }

}
