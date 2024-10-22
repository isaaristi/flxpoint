package com.example.flxpoint.Service;

import com.example.flxpoint.data.CustomerEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CustomerService {
    public CustomerEntity createCustomer(CustomerEntity customer) throws Exception;
    public CustomerEntity getCustomer(Long id) throws Exception;
    public List<CustomerEntity> getCustomers() throws Exception;
    public CustomerEntity updateCustomer(Long id, CustomerEntity customer) throws Exception;
    public void deleteCustomer(Long id) throws Exception;
}
