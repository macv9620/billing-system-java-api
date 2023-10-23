package com.macv.billing.service;

import com.macv.billing.persistence.entity.UserEntity;
import com.macv.billing.persistence.entity.UserRoleEntity;
import com.macv.billing.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//Este servicio conectará con repositorio de usuarios para dentro de la cadena}
// de validaciones consulte los usuarios desde la BD
@Service
public class UserSecurityService implements UserDetailsService {
    //Se inyecta repositorio de usuarios para poder consultar los usuarios en la BD
    private final UserRepository userRepository;

    @Autowired
    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Este método obliga la interfaz a implementarlo
    // en él a partir de los usuarios de la BD se crean usuarios de Spring
    // que la cadena consultará para validar credenciales...
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Se consulta id de usuario en cuestión
        UserEntity userEntity = userRepository.findById(username).
                orElseThrow(()-> new UsernameNotFoundException(
                        "User " + username + " Not found"
                ));

        String[] roles = userEntity.getRoles().stream().map(UserRoleEntity::getRoleName).toArray(String[]::new);


        // Se crea usuario de Spring Security para validación
        return User.builder()
                .username(userEntity.getUserId())
                .password(userEntity.getPassword())
                //En lugar de dar roles, se dan autorithies que tienen embedidos los roles también
                .authorities(this.grantedAuthorities(roles))
                //Si el usuario está bloquedado o deshabilitado
                .accountLocked(userEntity.getLocked())
                .disabled(userEntity.getDisabled())
                .build();
    }

    //Método para asociar un authority a un role
    private String[] getAuthorities(String role){
        //Validar si es un rol que debe tener acceso o parametrizado el authority
        if("ADMIN".equals(role) || "CUSTOMER".equals(role)){
            return new String[]{"create_invoice"};
        }

        //Si es cualquier otro role
        return new String[]{};
    }

    //Método que recibe los roles que previamente tiene el usuerio y de esta manera
    //pueda asignar aparte de roles también permisos individuales
    private List<GrantedAuthority> grantedAuthorities(String[] roles){
        List<GrantedAuthority> authorities = new ArrayList<>(roles.length);

        //Convertir cada role en authoritie esto mismo hace Spring automáticamente con .role
        for (String role: roles){
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

            //Validar qué rol es y asignar el authority que se debe tener
            for (String authority: this.getAuthorities(role)){
                //Añadir un nuevo SimpleGrantedAuthority pero esta vez un texto plano
                //con el authority definido en el método getAutorities
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }
        return authorities;
    }
}
