package com.macv.billing.service;

import com.macv.billing.persistence.entity.UserEntity;
import com.macv.billing.persistence.entity.UserRoleEntity;
import com.macv.billing.persistence.repository.UserRepository;
import com.macv.billing.persistence.repository.UserRoleRepository;
import com.macv.billing.service.customException.IncorrectCustomDataRequestException;
import com.macv.billing.service.validation.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public List<UserEntity> getAll(){
        return userRepository.findAll();
    }

    @Transactional
    public UserEntity createUser(UserEntity newUser){
        if(Objects.equals(newUser.getUserId(), "")){
            throw new IncorrectCustomDataRequestException("Invalid User id");
        } else if (Objects.equals(newUser.getName(), "")){
            throw new IncorrectCustomDataRequestException("Invalid User name");
        } else if (Objects.equals(newUser.getEmail(), "")){
            throw new IncorrectCustomDataRequestException("Invalid User email");
        }

        if (!EmailValidator.isValidEmail(newUser.getEmail())){
            throw new IncorrectCustomDataRequestException("Invalid email format [example: myuser@domain.es]");
        }

        if (userRepository.existsById(newUser.getUserId())){
            throw new IncorrectCustomDataRequestException("User already exists");

        }

        UserEntity newUserCreated = userRepository.save(newUser);

        UserRoleEntity defaultRole = new UserRoleEntity();
        defaultRole.setRoleName("CUSTOMER");
        defaultRole.setUserId(newUserCreated.getUserId());

        UserRoleEntity newInsertedRole = userRoleRepository.save(defaultRole);

        List<UserRoleEntity> rolesList = new ArrayList<>();
        rolesList.add(newInsertedRole);

        newUserCreated.setRoles(rolesList);

        return newUserCreated;
    }

    public UserEntity getUserById(String userId){
        if (!userRepository.existsById(userId)){
            throw new IncorrectCustomDataRequestException("User " + userId + " not found");
        }
        return userRepository.findById(userId).get();
    }
}
