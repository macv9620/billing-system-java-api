package com.macv.billing.web.controller;

import com.macv.billing.persistence.entity.CustomerEntity;
import com.macv.billing.service.dto.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CustomerEntity>> getAll(){
        return new ResponseEntity<>(customerService.getAll(), HttpStatus.OK);
    }
}
