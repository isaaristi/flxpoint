package com.example.flxpoint.Service.impl;

import com.example.flxpoint.Service.CrmService;
import com.example.flxpoint.data.CrmCustomer;
import com.example.flxpoint.data.CustomerEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Service
public class CrmServiceImpl implements CrmService {
    private Map<Long, CrmCustomer> crmDatabase = new HashMap<>();
    @Override
    public void syncCustomer(CustomerEntity customer) {
        if (isCrmDown()) { // Simular que el CRM está caído
            throw new RuntimeException("CRM service is currently unavailable");
        }
        CrmCustomer crmCustomer = mapToCrmCustomer(customer);
        crmDatabase.put(crmCustomer.getId(), crmCustomer);
        System.out.println("Syncing customer to CRM: " + crmCustomer);
    }
    private boolean isCrmDown() {
        return Math.random() < 0.2;
    }
    @Override
    public void syncCustomerWithRetry(CustomerEntity customer) {
        int attempts = 0;
        boolean success = false;

        while (attempts < 3 && !success) {
            try {
                syncCustomer(customer);
                success = true;
            } catch (RuntimeException e) {
                attempts++;
                if (attempts == 3) {
                    System.err.println("Sincronización fallida después de 3 intentos: " + e.getMessage());
                }
            }
        }
    }
    @Override
    public void deleteCustomer(Long id) {
        crmDatabase.remove(id);
        System.out.println("Deleted customer from CRM: " + id);
    }
    private CrmCustomer mapToCrmCustomer(CustomerEntity customer) {
        String fullName = customer.getFirstName() + " " + customer.getLastName();
        String location = String.join(", ", customer.getAddress().getStreet(), customer.getAddress().getCity(),
                customer.getAddress().getState(), customer.getAddress().getZipCode());
        return new CrmCustomer(customer.getCustomerId(), fullName, customer.getEmail(), customer.getPhoneNumber(), location);
    }
    @Override
    public Collection<CrmCustomer> getAllCustomers() {
        return crmDatabase.values(); // Devuelve todos los clientes en el "CRM"
    }
}
