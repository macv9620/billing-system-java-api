package com.macv.billing.service;

import com.macv.billing.persistence.entity.InvoiceEntity;
import com.macv.billing.persistence.entity.ProductEntity;
import com.macv.billing.persistence.repository.CustomerRepository;
import com.macv.billing.persistence.repository.InvoiceRepository;
import com.macv.billing.persistence.repository.ProductRepository;
import com.macv.billing.service.customException.IncorrectCustomDataRequestException;
import com.macv.billing.service.dto.NewBuyDto;
import com.macv.billing.service.dto.ProductSummaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public List<InvoiceEntity> getAll(){
        return invoiceRepository.findAll();
    }

    public List<InvoiceEntity> findByCustomerId(String  customerId){
        return invoiceRepository.findAllByCustomerId(customerId);
    }

    @Transactional
    public InvoiceEntity postNewBuy(NewBuyDto newBuyDto) throws IncorrectCustomDataRequestException{
        InvoiceEntity invoiceEntity = null;
        //Evaluar como acumular el total de la orden
        double orderTotal = 0;
        boolean customerExists = customerRepository.existsById(newBuyDto.getCustomerId());
        if (!customerExists){
            throw new IncorrectCustomDataRequestException("Invalid customer");
        } else if ((
                !Objects.equals(newBuyDto.getPaymentMethod(), "CASH") &&
                        !Objects.equals(newBuyDto.getPaymentMethod(), "CREDIT_CARD"))){
            throw new IncorrectCustomDataRequestException(
                    "Payment method should be CASH or CREDIT_CARD");
        }

        List<ProductSummaryDto> products = newBuyDto.getProducts();

        products.forEach(p -> {
            Optional<ProductEntity> productExists = productRepository.findById(p.getProductId());
            if (productExists.isEmpty()){
                    throw new IncorrectCustomDataRequestException("Product Id " + p.getProductId()
                    + " not found");
            }

            productExists.map(product ->{
               int dif = product.getStock() - p.getProductQuantity();
               if (dif < 0){
                   throw new IncorrectCustomDataRequestException("Product Id " + p.getProductId()
                           + " doesn't have enough stock");
               }

                return orderTotal;
            });
        });

        InvoiceEntity newInvoiceEntity = new InvoiceEntity();
        newInvoiceEntity.setCustomerId(newBuyDto.getCustomerId());
        newInvoiceEntity.setPaymentMethod(newBuyDto.getPaymentMethod());
        newInvoiceEntity.setUserComment(newBuyDto.getUserComment());

        return invoiceEntity;
    }
}
