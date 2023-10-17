package com.macv.billing.service.view;

import com.macv.billing.persistence.entity.view.SalesReportViewEntity;
import com.macv.billing.persistence.repository.ProductRepository;
import com.macv.billing.persistence.repository.view.SalesReportViewRepository;
import com.macv.billing.service.customException.IncorrectCustomDataRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesReportViewService {
    private final SalesReportViewRepository salesReportViewRepository;
    private final ProductRepository productRepository;

    public SalesReportViewService(SalesReportViewRepository salesReportViewRepository, ProductRepository productRepository) {
        this.salesReportViewRepository = salesReportViewRepository;
        this.productRepository = productRepository;
    }

    public List<SalesReportViewEntity> getSalesReport(int productId) throws IncorrectCustomDataRequestException {
        if(!productRepository.existsById(productId)){
            throw new IncorrectCustomDataRequestException("Product " + productId + " not found");
        }
        return salesReportViewRepository.getSalesReport(productId);
    }
}
