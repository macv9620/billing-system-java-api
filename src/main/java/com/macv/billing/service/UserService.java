package com.macv.billing.service;

import com.macv.billing.persistence.entity.UserEntity;
import com.macv.billing.persistence.repository.UserRepository;
import com.macv.billing.service.customException.IncorrectCustomDataRequestException;
import com.macv.billing.service.validation.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAll(){
        return userRepository.findAll();
    }

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
        return userRepository.save(newUser);
    }

    public UserEntity getUserById(String userId){
        if (!userRepository.existsById(userId)){
            throw new IncorrectCustomDataRequestException("User " + userId + " not found");
        }
        return userRepository.findById(userId).get();
    }
}
