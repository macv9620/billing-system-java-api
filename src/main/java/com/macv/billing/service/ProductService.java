package com.macv.billing.service;

import com.macv.billing.persistence.entity.BrandEntity;
import com.macv.billing.persistence.entity.CategoryEntity;
import com.macv.billing.persistence.entity.ProductEntity;
import com.macv.billing.persistence.entity.view.ProductStockAndPriceViewEntity;
import com.macv.billing.persistence.repository.BrandRepository;
import com.macv.billing.persistence.repository.CategoryRepository;
import com.macv.billing.persistence.repository.ProductRepository;
import com.macv.billing.persistence.repository.view.ProductStockAndPriceViewRepository;
import com.macv.billing.service.customException.IncorrectCustomDataRequestException;
import com.macv.billing.service.dto.UpdateStockDto;
import com.macv.billing.service.dto.UpdateUnitPriceDto;
import com.macv.billing.web.controller.wrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductStockAndPriceViewRepository productStockAndPriceViewRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductStockAndPriceViewRepository productStockAndPriceViewRepository, CategoryRepository categoryRepository, BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.productStockAndPriceViewRepository = productStockAndPriceViewRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    public List<ProductEntity> getAll(){
        return productRepository.findAllByOrderByProductIdAsc();
    }

    public ProductStockAndPriceViewEntity getStockAndPriceById(int productId){
        if (productRepository.findById(productId).isEmpty()){
            throw new IncorrectCustomDataRequestException("Product " + productId + " not found");
        }
        return productStockAndPriceViewRepository.getById(productId);
    }

    public ProductEntity create(ProductEntity productEntity){

        if (Objects.equals(productEntity.getName(), "")){
            throw new IncorrectCustomDataRequestException("Invalid product name");
        } else if (Objects.equals(productEntity.getDescription(), "")){
            throw new IncorrectCustomDataRequestException("Invalid product description");
        } else if(productEntity.getUnitPrice() <= 0){
            throw new IncorrectCustomDataRequestException("Unit price cannot be 0 o negative");
        } else if(productEntity.getStock() < 0){
            throw new IncorrectCustomDataRequestException("Stock cannot be negative");
        }

        if (!categoryRepository.existsById(productEntity.getCategoryId())){
            throw new IncorrectCustomDataRequestException("Category id doesn't exist");
        }

        if (!brandRepository.existsById(productEntity.getBrandId())){
            throw new IncorrectCustomDataRequestException("Brand id doesn't exist");
        }

        productEntity.setIsActive(true);
        ProductEntity newProductSaved = productRepository.save(productEntity);

        CategoryEntity categoryDetail = categoryRepository.findById(productEntity.getCategoryId()).get();
        BrandEntity brandDetail = brandRepository.findById(productEntity.getBrandId()).get();

        newProductSaved.setCategory(categoryDetail);
        newProductSaved.setBrand(brandDetail);

        return newProductSaved;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ProductEntity updateStock(UpdateStockDto updateStockDto){
        if(!productRepository.existsById(updateStockDto.getProductId())){
            throw new IncorrectCustomDataRequestException("Product id " + updateStockDto.getProductId() +
                     " doesn't exist");
        }
        if (updateStockDto.getNewStock() < 0){
            throw new IncorrectCustomDataRequestException("Stock cannot be negative");
        }

        int result = productRepository.stockUpdate(updateStockDto.getProductId(), updateStockDto.getNewStock());

        if (result != 1){
            throw new IncorrectCustomDataRequestException("Update couldn't be executed");
        } else {

            return productRepository.findById(updateStockDto.getProductId()).get();
        }

    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ProductEntity updateUnitPrice(UpdateUnitPriceDto updateUnitPriceDto){
        if(!productRepository.existsById(updateUnitPriceDto.getProductId())){
            throw new IncorrectCustomDataRequestException("Product id " + updateUnitPriceDto.getProductId() +
                    " doesn't exist");
        }
        if (updateUnitPriceDto.getNewUnitPrice() <= 0){
            throw new IncorrectCustomDataRequestException("Unit price cannot be negative or 0");
        }

        int result = productRepository.unitPriceUpdate(updateUnitPriceDto.getProductId(),
                updateUnitPriceDto.getNewUnitPrice());

        if (result != 1){
            throw new IncorrectCustomDataRequestException("Update couldn't be executed");
        } else {
            return productRepository.findById(updateUnitPriceDto.getProductId()).get();
        }

    }

    public ProductEntity getById(int productId){
        if (!productRepository.existsById(productId)){
            throw new IncorrectCustomDataRequestException("Product id " + productId + " not found");
        }
        return productRepository.findById(productId).get();
    }


}
