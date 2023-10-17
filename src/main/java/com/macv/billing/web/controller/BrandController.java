package com.macv.billing.web.controller;

import com.macv.billing.persistence.entity.BrandEntity;
import com.macv.billing.service.BrandService;
import com.macv.billing.web.controller.wrapper.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @Operation(summary = "Cosultar marcas", description = "Consulta todas las marcas de" +
            " productos creadas en la base de datos")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Listado de marcas",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BrandEntity.class)) })
    })
    @GetMapping("/getAll")
    public ResponseEntity<ResponseWrapper<?>> getAll(){
        String message;
        List<BrandEntity> data;
        HttpStatus httpStatus;

        try {
            data = brandService.getAll();
            message = data.size() + " brands found";
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
