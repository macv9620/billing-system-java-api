package com.macv.billing.web.controller;

import com.macv.billing.persistence.entity.ProductEntity;
import com.macv.billing.persistence.entity.view.ProductStockAndPriceViewEntity;
import com.macv.billing.service.ProductService;
import com.macv.billing.service.customException.IncorrectCustomDataRequestException;
import com.macv.billing.service.dto.UpdateStockDto;
import com.macv.billing.service.dto.UpdateUnitPriceDto;
import com.macv.billing.web.controller.wrapper.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseWrapper<?>> getAll() {
        String message;
        List<ProductEntity> data;
        HttpStatus httpStatus;

        try {
            data = productService.getAll();
            message = data.size() + " products found";
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            data = null;
            message = "Error no controlado, por favor contacte a soporte";
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(new ResponseWrapper<>(
                message,
                data
        ), httpStatus);
    }

    @GetMapping("/getStockAndPrice/{productId}")
    public ResponseEntity<ResponseWrapper<?>> getStockAndPriceById(@PathVariable int productId) {
        String message;
        ProductStockAndPriceViewEntity data;
        HttpStatus httpStatus;

        try {
            data = productService.getStockAndPriceById(productId);
            message = "Stock and price for product " + productId + " found";
            httpStatus = HttpStatus.OK;
        } catch (IncorrectCustomDataRequestException eCustom) {
            data = null;
            message = eCustom.getMessage();
            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
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

    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper<?>> create(@RequestBody ProductEntity productEntity){
        String message;
        ProductEntity data;
        HttpStatus httpStatus;

        try {
            data = productService.create(productEntity);
            message = "Product created successfully";
            httpStatus = HttpStatus.CREATED;
        } catch (IncorrectCustomDataRequestException eCustom) {
            data = null;
            message = eCustom.getMessage();
            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
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

    @PatchMapping("/updateStock")
    public ResponseEntity<ResponseWrapper<?>> updateStockById(@RequestBody UpdateStockDto updateStockDto){
        String message;
        ProductEntity data;
        HttpStatus httpStatus;

        try {
            data = productService.updateStock(updateStockDto);
            message = "Product stock updated successfully";
            httpStatus = HttpStatus.OK;
        } catch (IncorrectCustomDataRequestException eCustom) {
            data = null;
            message = eCustom.getMessage();
            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
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

    @PatchMapping("/updateUnitPrice")
    public ResponseEntity<ResponseWrapper<?>> updatePriceById(@RequestBody UpdateUnitPriceDto updateUnitPriceDto){
        String message;
        ProductEntity data;
        HttpStatus httpStatus;

        try {
            data = productService.updateUnitPrice(updateUnitPriceDto);
            message = "Product unit price updated successfully";
            httpStatus = HttpStatus.OK;
        } catch (IncorrectCustomDataRequestException eCustom) {
            data = null;
            message = eCustom.getMessage();
            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
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

    @GetMapping("/getById/{productId}")
    public ResponseEntity<ResponseWrapper<?>> getById(@PathVariable int productId){
        String message;
        ProductEntity data;
        HttpStatus httpStatus;

        try {
            data = productService.getById(productId);
            message = "Product created successfully";
            httpStatus = HttpStatus.CREATED;
        } catch (IncorrectCustomDataRequestException eCustom) {
            data = null;
            message = eCustom.getMessage();
            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
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
