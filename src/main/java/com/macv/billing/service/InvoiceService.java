package com.macv.billing.service;

import com.macv.billing.persistence.entity.InvoiceEntity;
import com.macv.billing.persistence.repository.InvoiceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<InvoiceEntity> getAll(){
        return invoiceRepository.findAll();
    }

    public List<InvoiceEntity> findByCustomerId(String  customerId){
        return invoiceRepository.findAllByCustomerId(customerId);
    }
}
