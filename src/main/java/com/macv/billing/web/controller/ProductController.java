package com.macv.billing.web.controller;

import com.macv.billing.persistence.entity.CategoryEntity;
import com.macv.billing.persistence.entity.ProductEntity;
import com.macv.billing.persistence.entity.view.ProductStockAndPriceViewEntity;
import com.macv.billing.service.ProductService;
import com.macv.billing.service.customException.IncorrectCustomDataRequestException;
import com.macv.billing.service.dto.UpdateStockDto;
import com.macv.billing.service.dto.UpdateUnitPriceDto;
import com.macv.billing.web.controller.wrapper.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@Tag(name="4. Product-Controller", description = "Endpoint para la gestión de productos, algunos métodos requieren autenticación con JWT y usuario con rol 'ADMIN'")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @Operation(summary = "Productos existentes / no requiere JWT", description = "Permite obtener todos los productos existentes")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Listado de productos",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class)) })
    })
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


    @Operation(summary = "Resumen de producto / requiere JWT rol 'ADMIN'", description = "Permite obtener el stock y precio unitario de un producto determinado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductStockAndPriceViewEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Errores en la petición",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class),
                            examples = {
                                    @ExampleObject(name = "Producto inexistente",
                                            value = """
                                                    {
                                                        "message": "Product id doesn't exist",
                                                        "data": null
                                                    }
                                                                                                        """)
                            }
                    )}),
    })
    @GetMapping("/getStockAndPrice/{productId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseWrapper<?>> getStockAndPriceById(
            @PathVariable
            @Parameter(name = "productId", description = "Identificador del producto", example = "10")
            int productId) {
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

    @Operation(summary = "Nuevo producto / requiere JWT rol 'ADMIN'", description = "Permite la creación de nuevos productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Errores en la petición",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class),
                            examples = {@ExampleObject(name = "Nombre o descripción inválidos",
                                    value = """
                                            {
                                                "message": "Invalid product name/description",
                                                "data": null
                                            }
                                            """),
                                    @ExampleObject(name = "Precio unitario inválido",
                                            value = """
                                                    {
                                                        "message": "Unit price cannot be 0 o negative",
                                                        "data": null
                                                    }
                                                                                                        """),
                                    @ExampleObject(name = "Stock inválido",
                                            value = """
                                                                    {
                                                                        "message": "Stock cannot be negative",
                                                                        "data": null
                                                                    }
                                                    """),
                                    @ExampleObject(name = "Categoria o marca inválidas",
                                            value = """
                                                                    {
                                                                         "message": "Category/Brand id doesn't exist",
                                                                         "data": null
                                                                                    }
                                                    """)
                            }
                    )}),
    })
    @PostMapping("/create")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseWrapper<?>> create(@RequestBody ProductEntity productEntity) {
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

    @Operation(summary = "Actualización de stock / requiere JWT rol 'ADMIN'", description = "Permite actualizar el stock de un producto determinado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Errores en la petición",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class),
                            examples = {
                                    @ExampleObject(name = "Stock inválido",
                                            value = """
                                                    {
                                                        "message": "Stock cannot be negative",
                                                        "data": null
                                                    }
                                                                                                        """),
                                    @ExampleObject(name = "Producto inexistente",
                                            value = """
                                                    {
                                                        "message": "Product id doesn't exist",
                                                        "data": null
                                                    }
                                                                                                        """)
                            }
                    )}),
    })
    @PatchMapping("/updateStock")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseWrapper<?>> updateStockById(@RequestBody UpdateStockDto updateStockDto) {
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


    @Operation(summary = "Actualización de precio / requiere JWT rol 'ADMIN'", description = "Permite actualizar el precio unitario de un producto determinado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Errores en la petición",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class),
                            examples = {
                                    @ExampleObject(name = "Precio unitario inválido",
                                            value = """
                                                    {
                                                        "message": "Unit price cannot be 0 o negative",
                                                        "data": null
                                                    }
                                                                                                        """),
                                    @ExampleObject(name = "Producto inexistente",
                                            value = """
                                                    {
                                                        "message": "Product id doesn't exist",
                                                        "data": null
                                                    }
                                                                                                        """)
                            }
                    )}),
    })
    @PatchMapping("/updateUnitPrice")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseWrapper<?>> updatePriceById(@RequestBody UpdateUnitPriceDto updateUnitPriceDto) {
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

    @Operation(summary = "Detalle de producto / requiere JWT rol 'ADMIN'", description = "Permite obtener la información detallada de un determinado producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Errores en la petición",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class),
                            examples = {
                                    @ExampleObject(name = "Producto inexistente",
                                            value = """
                                                    {
                                                        "message": "Product id doesn't exist",
                                                        "data": null
                                                    }
                                                                                                        """)
                            }
                    )}),
    })
    @GetMapping("/getById/{productId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseWrapper<?>> getById(
            @PathVariable
            @Parameter(name = "productId", description = "Identificador del producto", example = "10")
            int productId) {
        String message;
        ProductEntity data;
        HttpStatus httpStatus;

        try {
            data = productService.getById(productId);
            message = "Product found";
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

}
