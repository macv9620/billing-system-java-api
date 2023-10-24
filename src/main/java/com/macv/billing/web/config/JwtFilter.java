package com.macv.billing.web.config;

import com.macv.billing.service.UserSecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Notación para que la clase se pueda inyectar y entre al ciclo de vida de Spring
//OncePerRequestFilter permite posee un método a implementar que permite atrapar las peticiones
//a la api para hacer validaciones sobre ella como la validez del JWT por ejemplo
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserSecurityService userSecurityService;

    public JwtFilter(JwtUtil jwtUtil, UserSecurityService userSecurityService) {
        this.jwtUtil = jwtUtil;
        this.userSecurityService = userSecurityService;
    }

    //El método recibe el request que se envió en la petición
    //Un objeto con el que se puede parametrizar la respuesta del procesamiento de la validación
    //Inyecta recibe el filterChain porque esta clase hará parte del SecurityFilterChain
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //1. Validar que sea un Header Authorization válido
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer")){
            //.doFilter(): Causes the next filter in the chain to be invoked, or if the calling filter is the last
            //filter in the chain, causes the resource at the end of the chain to be invoked.
            filterChain.doFilter(request, response);

            //Evitar que se sigan haciendo validaciones
            return;
        }

        //2. Validar que el JWT sea válido
        //capturar el jwt de la petición, creando un array separando por un espacio el
        //String del authority que llega en el header, .trim asegura que no tenga espacios ni antes ni después
        String jwt = authHeader.split(" ")[1].trim();
        if (!jwtUtil.validateToken(jwt)){
            filterChain.doFilter(request, response);
            return;
        }

        //3. Cargar el usuario del UserDetailService
        //Obtener el username del JWT
        String username = jwtUtil.getUsername(jwt);
        //Buscar el usuario en la BD y retornarlo al filtro
        User userLoaded = (User) userSecurityService.loadUserByUsername(username);

        //4. Cargar al usuario en el contexto de seguridad
        //Cuando se carga el usuario Spring entiende que pasó los filtros y continua con el SecurityChainFilter
        //Validando permisos y lo demás, esta carga le indica a los otros filtros que la pertición
        //se resolvió de manera correcta en términos de seguridad

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userLoaded.getUsername(),
                        userLoaded.getPassword(),
                        userLoaded.getAuthorities()
                );
        //cargar usuario al contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
