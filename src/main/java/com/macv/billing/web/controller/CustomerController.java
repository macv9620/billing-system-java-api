package com.macv.billing.web.controller;

import com.macv.billing.persistence.entity.CustomerEntity;
import com.macv.billing.service.CustomerService;
import com.macv.billing.service.customException.IncorrectCustomDataRequestException;
import com.macv.billing.web.controller.wrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseWrapper<?>> getAll(){

        String message;
        List<CustomerEntity> data;
        HttpStatus httpStatus;

        try {
            data = customerService.getAll();
            message = data.size() + " customers found";
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

    @PostMapping("/createCustomer")
    public ResponseEntity<ResponseWrapper<?>> createCustomer(@RequestBody CustomerEntity newCustomer){

        String message;
        CustomerEntity data;
        HttpStatus httpStatus;

        try {
            data = customerService.createCustomer(newCustomer);
            message = "Customer created successfully";
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

    @GetMapping("/getById/{customerId}")
    public ResponseEntity<ResponseWrapper<?>> getById(@PathVariable String customerId){
        String message;
        CustomerEntity data;
        HttpStatus httpStatus;

        try {
            data = customerService.getCustomerById(customerId);
            message = "Customer found";
            httpStatus = HttpStatus.OK;
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
