package com.macv.billing.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "user_role")
public class UserRoleEntity {

    @Id
    @Column(name = "id_user")
    private String userId;

    @Column(name = "role_name")
    private String roleName;

    @CreationTimestamp
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

