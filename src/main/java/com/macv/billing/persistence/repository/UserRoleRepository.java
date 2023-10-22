package com.macv.billing.persistence.repository;

import com.macv.billing.persistence.entity.UserEntity;
import com.macv.billing.persistence.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, String> {
}
