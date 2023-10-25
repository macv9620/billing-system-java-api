package com.macv.billing.web.controller;

import com.macv.billing.persistence.entity.UserEntity;
import com.macv.billing.persistence.entity.ProductEntity;
import com.macv.billing.service.UserService;
import com.macv.billing.service.customException.IncorrectCustomDataRequestException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name="6. User-Controller", description = "Endpoint para la gestión de usuarios")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Clientes existentes / requiere JWT rol 'ADMIN'", description = "Permite obtener todos los clientes existentes")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Listado de clientes",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class)) })
    })
    @GetMapping("/getAll")
    public ResponseEntity<ResponseWrapper<?>> getAll(){

        String message;
        List<UserEntity> data;
        HttpStatus httpStatus;

        try {
            data = userService.getAll();
            message = data.size() + " users found";
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


    @Operation(summary = "Crear cliente / requiere JWT rol 'ADMIN'", description = "Permite la creación de nuevos clientes, por defecto los nuevos clientes creados tienen el rol 'CUSTOMER'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Errores en la petición",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class),
                            examples = {@ExampleObject(name = "Nombre o identificación inválidos",
                                    value = """
                                            {
                                                "message": "Invalid User name/id",
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
                                                        "message": "User already exists",
                                                        "data": null
                                                    }
                                                                                                        """)
                            }
                    )}),
    })
    @PostMapping("/createUser")
    public ResponseEntity<ResponseWrapper<?>> createUser(@RequestBody UserEntity newUser){

        String message;
        UserEntity data;
        HttpStatus httpStatus;

        try {
            data = userService.createUser(newUser);
            message = "User created successfully";
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

    @Operation(summary = "Detalle de cliente / requiere JWT rol 'ADMIN'", description = "Permite obtener la información detallada de un determinado cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Errores en la petición",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class),
                            examples = {
                                    @ExampleObject(name = "Cliente inexistente",
                                            value = """
                                                    {
                                                        "message": "User not found",
                                                        "data": null
                                                    }
                                                                                                        """)
                            }
                    )}),
    })
    @GetMapping("/getById/{userId}")
    public ResponseEntity<ResponseWrapper<?>> getById(
            @PathVariable
            @Parameter(name = "userId", description = "Identificador del cliente", example = "879545")
            String userId){
        String message;
        UserEntity data;
        HttpStatus httpStatus;

        try {
            data = userService.getUserById(userId);
            message = "User found";
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
