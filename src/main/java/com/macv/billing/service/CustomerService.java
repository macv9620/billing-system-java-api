package com.macv.billing.service;

import com.macv.billing.persistence.entity.CustomerEntity;
import com.macv.billing.persistence.repository.CustomerRepository;
import com.macv.billing.service.customException.IncorrectCustomDataRequestException;
import com.macv.billing.service.validation.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerEntity> getAll(){
        return customerRepository.findAll();
    }

    public CustomerEntity createCustomer(CustomerEntity newCustomer){
        if(Objects.equals(newCustomer.getCustomerId(), "")){
            throw new IncorrectCustomDataRequestException("Invalid Customer id");
        } else if (Objects.equals(newCustomer.getName(), "")){
            throw new IncorrectCustomDataRequestException("Invalid Customer name");
        } else if (Objects.equals(newCustomer.getEmail(), "")){
            throw new IncorrectCustomDataRequestException("Invalid Customer email");
        }

        if (!EmailValidator.isValidEmail(newCustomer.getEmail())){
            throw new IncorrectCustomDataRequestException("Invalid email format [example: myuser@domain.es]");
        }

        if (customerRepository.existsById(newCustomer.getCustomerId())){
            throw new IncorrectCustomDataRequestException("Customer already exists");

        }
        return customerRepository.save(newCustomer);
    }

    public CustomerEntity getCustomerById(String customerId){
        if (!customerRepository.existsById(customerId)){
            throw new IncorrectCustomDataRequestException("Customer " + customerId + " not found");
        }
        return customerRepository.findById(customerId).get();
    }
}
