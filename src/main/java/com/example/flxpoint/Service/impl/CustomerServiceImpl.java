package com.example.flxpoint.Service.impl;

import com.example.flxpoint.Service.CustomerService;
import com.example.flxpoint.data.CustomerEntity;
import com.example.flxpoint.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CrmServiceImpl crmService;
    @Override
    public CustomerEntity createCustomer(CustomerEntity customer) throws Exception {
        CustomerEntity savedCustomer = customerRepository.save(customer);
        crmService.syncCustomer(savedCustomer);
        return savedCustomer;
    }

    @Override
    public CustomerEntity getCustomer(Long id) throws Exception {
        return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    @Override
    public List<CustomerEntity> getCustomers() throws Exception {
        return customerRepository.findAll();
    }

    @Override
    public CustomerEntity updateCustomer(Long id, CustomerEntity customer) throws Exception {
        customer.setCustomerId(id);
        CustomerEntity updatedCustomer = customerRepository.save(customer);
        crmService.syncCustomer(updatedCustomer);
        return updatedCustomer;
    }

    @Override
    public void deleteCustomer(Long id) throws Exception {
        customerRepository.deleteById(id);
        crmService.deleteCustomer(id);
    }
}
