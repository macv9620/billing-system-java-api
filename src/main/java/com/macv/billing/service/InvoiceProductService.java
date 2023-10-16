package com.macv.billing.service;

import com.macv.billing.persistence.entity.InvoiceProductEntity;
import com.macv.billing.persistence.repository.InvoiceProductRepository;
import org.springframework.stereotype.Service;

@Service
public class InvoiceProductService {
    private final InvoiceProductRepository invoiceProductRepository;

    public InvoiceProductService(InvoiceProductRepository invoiceProductRepository) {
        this.invoiceProductRepository = invoiceProductRepository;
    }

    public InvoiceProductEntity postNewInvoiceService(InvoiceProductEntity invoiceProductEntity){
        return invoiceProductRepository.save(invoiceProductEntity);
    }
}
