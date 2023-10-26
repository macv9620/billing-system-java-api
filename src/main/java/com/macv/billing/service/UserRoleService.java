package com.macv.billing.service;

import com.macv.billing.persistence.entity.UserRoleEntity;
import com.macv.billing.persistence.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public UserRoleEntity create(UserRoleEntity userRoleEntity){
        return userRoleRepository.save(userRoleEntity);
    }
}
