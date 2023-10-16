package com.macv.billing.web.controller;

import com.macv.billing.persistence.entity.CustomerEntity;
import com.macv.billing.persistence.entity.InvoiceEntity;
import com.macv.billing.persistence.entity.ProductEntity;
import com.macv.billing.service.InvoiceService;
import com.macv.billing.service.customException.IncorrectCustomDataRequestException;
import com.macv.billing.service.dto.NewBuyDto;
import com.macv.billing.web.controller.wrapper.ResponseWrapper;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/newBuy")
    public ResponseEntity<ResponseWrapper<?>> postNewBuy(@RequestBody NewBuyDto newBuyDto){

        String message;
        InvoiceEntity data;
        HttpStatus httpStatus;

        try {
            data = invoiceService.postNewBuy(newBuyDto);
            message = "Invoice created successfully";
            httpStatus = HttpStatus.CREATED;
        } catch (IncorrectCustomDataRequestException eCustom){
            data = null;
            message = eCustom.getMessage();
            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (Exception e){
            data = null;
            message = "Error no controlado";
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(new ResponseWrapper<>(
                message,
                data
        ), httpStatus);
    }

}
