package com.macv.billing.web.controller;

import com.macv.billing.persistence.entity.CategoryEntity;
import com.macv.billing.service.CategoryService;
import com.macv.billing.web.controller.wrapper.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Consultar categorias", description = "Consulta todas las categorias de" +
            " productos creadas en la base de datos")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Listado de categorias",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryEntity.class)) })
    })
    @GetMapping("/getAll")
    public ResponseEntity<ResponseWrapper<List<CategoryEntity>>> getAll(){
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
