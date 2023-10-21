package com.macv.billing.service;

import com.macv.billing.persistence.entity.InvoiceEntity;
import com.macv.billing.persistence.entity.InvoiceProductEntity;
import com.macv.billing.persistence.entity.SalesReportEntity;
import com.macv.billing.persistence.entity.ProductEntity;
import com.macv.billing.persistence.repository.*;
import com.macv.billing.service.customException.IncorrectCustomDataRequestException;
import com.macv.billing.service.dto.NewBuyDto;
import com.macv.billing.service.dto.ProductSummaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final InvoiceProductRepository invoiceProductRepository;
    private final SalesReportRepository salesReportRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, UserRepository userRepository, ProductRepository productRepository, InvoiceProductRepository invoiceProductRepository, SalesReportRepository salesReportRepository) {
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.invoiceProductRepository = invoiceProductRepository;
        this.salesReportRepository = salesReportRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<InvoiceEntity> getAll() {
        return invoiceRepository.findAllByOrderByInvoiceIdDesc();
    }

    public List<InvoiceEntity> findByUserId(String userId) {
        return invoiceRepository.findAllByUserIdOrderByInvoiceIdDesc(userId);
    }

    @Transactional
    public InvoiceEntity postNewBuy(NewBuyDto newBuyDto) throws IncorrectCustomDataRequestException {

        double orderTotal = 0.00;
        HashMap<Integer, ProductEntity> productsDb = new HashMap<>();
        List<InvoiceProductEntity> newProductsToSet = new ArrayList<>();
        List<Integer> productIds = new ArrayList<>();


        boolean userExists = userRepository.existsById(newBuyDto.getUserId());

        if (!userExists) {
            throw new IncorrectCustomDataRequestException("Invalid user");
        } else if ((
                !Objects.equals(newBuyDto.getPaymentMethod(), "CASH") &&
                        !Objects.equals(newBuyDto.getPaymentMethod(), "CREDIT_CARD"))) {
            throw new IncorrectCustomDataRequestException(
                    "Payment method must be CASH or CREDIT_CARD");
        }

        List<ProductSummaryDto> invoiceProducts = newBuyDto.getProducts();

        for (ProductSummaryDto invoiceProduct : invoiceProducts) {
            if(invoiceProduct.getProductQuantity()<=0){
                throw new IncorrectCustomDataRequestException("Product quantity cannot be 0 or negative");
            }
            Optional<ProductEntity> productFound = productRepository.findById(invoiceProduct.getProductId());
            if (productFound.isEmpty()) {
                throw new IncorrectCustomDataRequestException("Product Id " + invoiceProduct.getProductId()
                        + " not found");
            }

            if(productIds.contains(invoiceProduct.getProductId())){
                throw new IncorrectCustomDataRequestException("Product Id " + invoiceProduct.getProductId()
                        + " is duplicated");
            }

            productIds.add(invoiceProduct.getProductId());

            ProductEntity product = productFound.get();
            int dif = product.getStock() - invoiceProduct.getProductQuantity();
            if (dif < 0) {
                throw new IncorrectCustomDataRequestException("Product Id " + invoiceProduct.getProductId()
                        + " doesn't have enough stock");
            }

            orderTotal +=
                    invoiceProduct.getProductQuantity() * product.getUnitPrice();

            productsDb.put(product.getProductId(), product);
        }


        InvoiceEntity newInvoiceEntity = new InvoiceEntity();
        newInvoiceEntity.setUserId(newBuyDto.getUserId());
        newInvoiceEntity.setPaymentMethod(newBuyDto.getPaymentMethod());
        newInvoiceEntity.setUserComment(newBuyDto.getUserComment());
        newInvoiceEntity.setInvoiceTotal((double) Math.round(orderTotal * 100) / 100);

        InvoiceEntity invoiceEntitySaved = invoiceRepository.save(newInvoiceEntity);

        for (ProductSummaryDto newProductInvoiceToPost : invoiceProducts) {

            int productId = newProductInvoiceToPost.getProductId();
            int currentStock = productsDb.get(productId).getStock();
            int quantityOut = newProductInvoiceToPost.getProductQuantity();
            int updatedStock = currentStock - quantityOut;
            String productName = productsDb.get(productId).getName();

            InvoiceProductEntity invoiceProductEntityToPost = new InvoiceProductEntity();
            invoiceProductEntityToPost.setInvoiceId(invoiceEntitySaved.getInvoiceId());
            invoiceProductEntityToPost.setProductId(productId);
            invoiceProductEntityToPost.setProductQuantity(quantityOut);
            invoiceProductEntityToPost.setProductTotalPrice(
                    quantityOut * productsDb.get(productId).getUnitPrice()
            );

            productRepository.stockOut(productId, updatedStock);

            SalesReportEntity salesReportRegistration = new SalesReportEntity();
            salesReportRegistration.setProductId(productId);
            salesReportRegistration.setInvoiceId(invoiceEntitySaved.getInvoiceId());
            salesReportRegistration.setProductName(productName);
            salesReportRegistration.setTransactionType("OUT");
            salesReportRegistration.setInitialStock(currentStock);
            salesReportRegistration.setTransactionQuantity(quantityOut);
            salesReportRegistration.setFinalStock(updatedStock);
            salesReportRepository.save(salesReportRegistration);

            InvoiceProductEntity newProductInvoicePosted = invoiceProductRepository.save(invoiceProductEntityToPost);
            newProductsToSet.add(newProductInvoicePosted);
        }

        invoiceEntitySaved.setProducts(newProductsToSet);
        return invoiceEntitySaved;
    }
}
