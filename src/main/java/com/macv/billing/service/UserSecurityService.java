package com.macv.billing.service;

import com.macv.billing.persistence.entity.UserEntity;
import com.macv.billing.persistence.entity.UserRoleEntity;
import com.macv.billing.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
                //En inicio el rol será admin, en un momento esto pasará también a la BD
                .roles(roles)
                //Si el usuario está bloquedado o deshabilitado
                .accountLocked(userEntity.getLocked())
                .disabled(userEntity.getDisabled())
                .build();
    }
}
