package com.macv.billing.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "user_role")
public class UserRoleEntity {

    @Id
    @Column(name = "id_user")
    @Schema(description = "Identificación de usuario",
            requiredMode = Schema.RequiredMode.AUTO, example = "8895874",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String userId;

    @Column(name = "role_name")
    @Schema(description = "Rol de usuario, determina las autorizaciones que tiene el usuario al generar peticiones a la API",
            requiredMode = Schema.RequiredMode.AUTO, example = "CUSTOMER",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String roleName;

    @CreationTimestamp
    @Schema(description = "Fecha de asignación autorizaciones",
            requiredMode = Schema.RequiredMode.AUTO,
            accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "granted_date")
    private Date grantedDate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", updatable = false, insertable = false)
    private UserEntity user;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getGrantedDate() {
        return grantedDate;
    }

    public void setGrantedDate(Date grantedDate) {
        this.grantedDate = grantedDate;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}

