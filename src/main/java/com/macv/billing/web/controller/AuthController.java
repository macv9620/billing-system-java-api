package com.macv.billing.web.controller;

import com.macv.billing.service.dto.AuthRequestLoginDto;
import com.macv.billing.service.dto.AuthResponseLoginDto;
import com.macv.billing.web.config.JwtUtil;
import com.macv.billing.web.controller.wrapper.ResponseWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper<?>> login(@RequestBody AuthRequestLoginDto authRequestLoginDto){
        System.out.println(authRequestLoginDto);
        //Crear usuario del ciclo de vida de Spring enviándole como parámetros el usuario y la contraseña
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(
                authRequestLoginDto.getUsername(),
                authRequestLoginDto.getPassword()
        );

        //Autenticar el usuario pasándolo por el authenticationManager
        //IMPORTANTE: si las credenciales no pasan o son inválidas esta línea arroja un error
        //si no arroja un error y continua, entonces las credenciales son correctas
        //si las credenciales no son válidas
        //Internamente este método va a llamar el AuthenticationProvider que a su vez
        //llama al UserDetailService que será el que se construyó, el UserSecurityService
        Authentication authentication = this.authenticationManager.authenticate(login);

        //Indica si se logró o no la authenticacion
        System.out.println(authentication.isAuthenticated());

        //Indica el usuario autenticado
        System.out.println(authentication.getPrincipal());

        //Crear JWT que se enviará en la respuesta, esta clase debe ser inyectada
        String jwt = jwtUtil.create(authRequestLoginDto.getUsername());

        AuthResponseLoginDto data = new AuthResponseLoginDto();
        data.setToken(jwt);

        ResponseWrapper<AuthResponseLoginDto> responseWrapper = new ResponseWrapper<>(
                "User successfully logged in",
                data
        );

        return ResponseEntity
                .ok()
                .header(HttpHeaders.AUTHORIZATION, jwt)
                .body(responseWrapper);
    }
}
