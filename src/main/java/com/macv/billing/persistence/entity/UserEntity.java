package com.macv.billing.persistence.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="\"user\"")
public class UserEntity {

    @Id
    @Column(name = "id_user")
    @Schema(description = "Identificación o documento del usuario",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "789657")
    private String userId;

    @Schema(description = "Nombre completo del usuario",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "Maria Córdoba")
    private String name;

    @Schema(description = "Dirección de correo electrónico del usuario",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "maria@gmail.com")
    private String email;

    @Schema(description = "Indica si el usuario se encuentra bloqueado",
            requiredMode = Schema.RequiredMode.AUTO, example = "falso",
            accessMode = Schema.AccessMode.READ_ONLY)
    private boolean locked;

    @Schema(description = "Indica si el usuario se encuentra deshabilitado",
            requiredMode = Schema.RequiredMode.AUTO, example = "falso",
            accessMode = Schema.AccessMode.READ_ONLY)
    private boolean disabled;

    @Schema(description = "Indica la clave del usuario, para fines de prueba e ilustrativos se expone este campo, teniendo en cuenta que el deber ser es no hacerlo",
            requiredMode = Schema.RequiredMode.AUTO, example = "pruebas0000",
            accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name="\"password\"")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // This is to make sure the attribute is write-only
    private String password;

    @Schema(description = "Indica la fecha de creación del usuario",
            requiredMode = Schema.RequiredMode.AUTO,
            accessMode = Schema.AccessMode.READ_ONLY)
    @CreationTimestamp
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserRoleEntity> roles;


    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String plainTextPassword) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(plainTextPassword);
    }

    public boolean getLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public PasswordEncoder passwordEncoder(){
        //Tipo de codificado comunmente empleado
        return new BCryptPasswordEncoder();
    }
}
