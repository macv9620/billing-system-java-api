package com.macv.billing.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.validation.annotation.Validated;

@Entity
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @Column(name = "id_customer")
    private String customerId;

    private String name;
    private String email;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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
}
