package com.macv.billing.web.controller;

import com.macv.billing.persistence.entity.UserEntity;
import com.macv.billing.persistence.entity.InvoiceEntity;
import com.macv.billing.persistence.entity.ProductEntity;
import com.macv.billing.service.InvoiceService;
import com.macv.billing.service.customException.IncorrectCustomDataRequestException;
import com.macv.billing.service.dto.NewBuyDto;
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
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoice")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name="7. Invoice-Controller", description = "Endpoint para la gestion y consulta de facturas")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Operation(summary = "Facturas existentes / requiere JWT rol 'ADMIN'", description = "Permite obtener todas las facturas de venta existentes y los productos que la componen")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Listado de facturas",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvoiceEntity.class)) })
    })
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

    @Operation(summary = "Facturas por cliente / requiere JWT rol 'ADMIN'", description = "Permite obtener las facturas asociadas a un determinado cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de facturas",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvoiceEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Errores en la petición",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class),
                            examples = {
                                    @ExampleObject(name = "Cliente inexistente",
                                            value = """
                                                    {
                                                        "message": "0 invoices for user",
                                                        "data": []
                                                    }
                                                                                                        """)
                            }
                    )}),
    })
    @GetMapping("/getByUser")
    public ResponseEntity<ResponseWrapper<?>> getByUserId(
            @RequestParam("id")
            @Parameter(name = "id", description = "Identificador del cliente", example = "879545")
            String userId){
        String message;
        List<InvoiceEntity> data;
        HttpStatus httpStatus;

        try {
            data = invoiceService.findByUserId(userId);
            message = data.size() + " invoices found for user " + userId;
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


    @Operation(summary = "Registrar compra / requiere JWT rol 'ADMIN' o 'CUSTOMER'", description = "Permite la creación de nuevas compras o facturas, el método crea la factura y descuenta del inventario la cantidad comprada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Compra registrada exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvoiceEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Errores en la petición",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class),
                            examples = {@ExampleObject(name = "Cliente inválido",
                                    value = """
                                            {
                                                "message": "Invalid user",
                                                "data": null
                                            }
                                            """),
                                    @ExampleObject(name = "Método de pago inválido",
                                            value = """
                                                    {
                                                        "message": "Payment method must be CASH or CREDIT_CARD",
                                                        "data": null
                                                    }
                                                                                                        """),
                                    @ExampleObject(name = "Cantidad del producto inválida",
                                            value = """
                                                                    {
                                                                        "message": "Product quantity cannot be 0 or negative",
                                                                        "data": null
                                                                    }
                                                    """),
                                    @ExampleObject(name = "Producto inexistente",
                                            value = """
                                                                    {
                                                                         "message": "Product Id not found",
                                                                         "data": null
                                                                                    }
                                                    """),
                                    @ExampleObject(name = "Producto duplicado en petición",
                                            value = """
                                                                    {
                                                                         "message": "Product Id is duplicated",
                                                                         "data": null
                                                                                    }
                                                    """),
                                    @ExampleObject(name = "Producto sin suficiente stock para compra",
                                            value = """
                                                                    {
                                                                         "message": "Product Id doesn't have enough stock",
                                                                         "data": null
                                                                                    }
                                                    """)
                            }
                    )}),
    })
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
