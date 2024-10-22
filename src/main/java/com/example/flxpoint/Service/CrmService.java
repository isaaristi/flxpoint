package com.example.flxpoint.Service;

import com.example.flxpoint.data.CrmCustomer;
import com.example.flxpoint.data.CustomerEntity;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public interface CrmService {
    void syncCustomer(CustomerEntity customer) throws Exception;
    void syncCustomerWithRetry(CustomerEntity customer) throws Exception;
    void deleteCustomer(Long id) throws Exception;
    Collection<CrmCustomer> getAllCustomers() throws Exception;

}
