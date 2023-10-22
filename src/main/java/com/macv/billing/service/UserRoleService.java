package com.macv.billing.service;

import com.macv.billing.persistence.entity.UserRoleEntity;
import com.macv.billing.persistence.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public UserRoleEntity create(UserRoleEntity userRoleEntity){
        return userRoleRepository.save(userRoleEntity);
    }
}
