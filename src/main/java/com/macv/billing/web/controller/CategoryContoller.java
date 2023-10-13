package com.macv.billing.web.controller;

import com.macv.billing.persistence.entity.BrandEntity;
import com.macv.billing.persistence.entity.CategoryEntity;
import com.macv.billing.service.CategoryService;
import com.macv.billing.web.controller.wrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryContoller {
    private final CategoryService categoryService;

    @Autowired
    public CategoryContoller(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseWrapper<?>> getAll(){
        String message;
        List<CategoryEntity> data;
        HttpStatus httpStatus;

        try {
            data = categoryService.getAll();
            message = data.size() + " categories found";
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
