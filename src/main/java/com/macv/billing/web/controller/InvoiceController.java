package com.macv.billing.web.controller;

import com.macv.billing.persistence.entity.CustomerEntity;
import com.macv.billing.persistence.entity.InvoiceEntity;
import com.macv.billing.service.InvoiceService;
import com.macv.billing.web.controller.wrapper.ResponseWrapper;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseWrapper<?>> getAll(){
        String message;
        List<InvoiceEntity> data;
        HttpStatus httpStatus;

        try {
            data = invoiceService.getAll();
            message = data.size() + " invoices found";
            httpStatus = HttpStatus.OK;
        } catch (Exception e){
            data = null;
            message = "Error no controlado, por favor contacte a soporte";
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(new ResponseWrapper<>(
                message,
                data
        ), httpStatus);
    }

    @GetMapping("/getByCustomer")
    public ResponseEntity<ResponseWrapper<?>> getByCustomerId(@RequestParam("id") String customerId){
        String message;
        List<InvoiceEntity> data;
        HttpStatus httpStatus;

        try {
            data = invoiceService.findByCustomerId(customerId);
            message = data.size() + " invoices found for customer " + customerId;
            httpStatus = HttpStatus.OK;
        } catch (Exception e){
            data = null;
            message = "Error no controlado, por favor contacte a soporte";
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(new ResponseWrapper<>(
                message,
                data
        ), httpStatus);
    }
}
