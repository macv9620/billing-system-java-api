package com.macv.billing.web.controller.view;

import com.macv.billing.persistence.entity.UserEntity;
import com.macv.billing.persistence.entity.ProductEntity;
import com.macv.billing.persistence.entity.view.SalesReportViewEntity;
import com.macv.billing.service.customException.IncorrectCustomDataRequestException;
import com.macv.billing.service.view.SalesReportViewService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/salesReport")
@Tag(name="5. SalesReport-Controller", description = "Endpoint generar un reporte de ventas por producto")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Falla autenticación, token no enviado en el Header o token inválido",
                content = {@Content(mediaType = "application/json",
                        examples = {
                                @ExampleObject(
                                        value = """
                                                    {
                                                        "message": "Invalid token"
                                                    }
                                                                                                        """)
                        }
                )}),
        @ApiResponse(responseCode = "403", description = "Usuario sin permisos para acceder al recurso",
                content = {@Content(mediaType = "application/json",
                        examples = {
                                @ExampleObject(
                                        value = """
                                                    {
                                                        "message": "Unauthorized"
                                                    }
                                                                                                        """)
                        }
                )})
})
public class SalesReportViewController {
    private final SalesReportViewService salesReportViewService;

    public SalesReportViewController(SalesReportViewService salesReportViewService) {
        this.salesReportViewService = salesReportViewService;
    }


    @Operation(summary = "Compras asociadas a un producto / requiere JWT rol 'ADMIN'",
            description = "Genera un reporte de todas las compras asociadas a un producto incluyendo información relavante de la transacción, stock, medio de pago, cliente, fecha entre otros")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Listado de compras",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SalesReportViewEntity.class)) })
    })
    @GetMapping("/getAllSales/{productId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseWrapper<?>> getSalesReport(
            @Parameter(name = "productId", description = "Identificador del producto para consultar su historial de compras",
            example = "12")
            @PathVariable("productId") int productId){

        String message;
        List<SalesReportViewEntity> data;
        HttpStatus httpStatus;

        try {
            data = salesReportViewService.getSalesReport(productId);
            message = data.size() + " sales found for product " + productId;
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
