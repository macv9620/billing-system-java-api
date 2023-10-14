package com.macv.billing.web.controller;

import com.macv.billing.persistence.entity.ProductEntity;
import com.macv.billing.service.ProductService;
import com.macv.billing.web.controller.wrapper.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseWrapper<?>> getAll(){
        String message;
        List<ProductEntity> data;
        HttpStatus httpStatus;

        try {
            data = productService.getAll();
            message = data.size() + " products found";
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
