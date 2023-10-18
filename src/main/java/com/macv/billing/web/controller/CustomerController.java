package com.macv.billing.web.controller;

import com.macv.billing.persistence.entity.CustomerEntity;
import com.macv.billing.persistence.entity.ProductEntity;
import com.macv.billing.service.CustomerService;
import com.macv.billing.service.customException.IncorrectCustomDataRequestException;
import com.macv.billing.web.controller.wrapper.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Clientes existentes", description = "Permite obtener todos los clientes existentes")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Listado de clientes",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerEntity.class)) })
    })
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


    @Operation(summary = "Crear cliente", description = "Permite la creación de nuevos clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Errores en la petición",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class),
                            examples = {@ExampleObject(name = "Nombre o identificación inválidos",
                                    value = """
                                            {
                                                "message": "Invalid Customer name/id",
                                                "data": null
                                            }
                                            """),
                                    @ExampleObject(name = "Email inválido",
                                            value = """
                                                    {
                                                        "message": "Invalid email format [example: myuser@domain.es]",
                                                        "data": null
                                                    }
                                                                                                        """),
                                    @ExampleObject(name = "Cliente existente",
                                            value = """
                                                    {
                                                        "message": "Customer already exists",
                                                        "data": null
                                                    }
                                                                                                        """)
                            }
                    )}),
    })
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

    @Operation(summary = "Detalle de cliente", description = "Permite obtener la información detallada de un determinado cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Errores en la petición",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class),
                            examples = {
                                    @ExampleObject(name = "Cliente inexistente",
                                            value = """
                                                    {
                                                        "message": "Customer not found",
                                                        "data": null
                                                    }
                                                                                                        """)
                            }
                    )}),
    })
    @GetMapping("/getById/{customerId}")
    public ResponseEntity<ResponseWrapper<?>> getById(
            @PathVariable
            @Parameter(name = "customerId", description = "Identificador del cliente", example = "879545")
            String customerId){
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
