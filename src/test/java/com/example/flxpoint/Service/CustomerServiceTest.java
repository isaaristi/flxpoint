package com.example.flxpoint.Service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.flxpoint.Service.impl.CustomerServiceImpl;
import com.example.flxpoint.data.Address;
import com.example.flxpoint.data.CustomerEntity;
import com.example.flxpoint.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
public class CustomerServiceTest {

        @Mock
        private CustomerRepository customerRepository;

        @InjectMocks
        private CustomerServiceImpl customerService;

        private CustomerEntity customer;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
            customer = new CustomerEntity();
            customer.setCustomerId(1L);
            customer.setFirstName("John");
            customer.setLastName("Doe");
            customer.setEmail("john.doe@example.com");
            customer.setPhoneNumber("123456789");
            Address address = new Address();
            address.setStreet("123 Main St");
            address.setCity("Springfield");
            address.setState("IL");
            address.setZipCode("62704");
            customer.setAddress(address);
        }

        @Test
        public void testCreateCustomer() throws Exception {
            when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customer);
            CustomerEntity savedCustomer = customerService.createCustomer(customer);
            assertEquals(customer.getFirstName(), savedCustomer.getFirstName());
            verify(customerRepository, times(1)).save(customer);
        }

        @Test
        public void testGetCustomer() throws Exception {
            when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
            CustomerEntity foundCustomer = customerService.getCustomer(1L);
            assertEquals(customer.getFirstName(), foundCustomer.getFirstName());
        }

        @Test
        public void testUpdateCustomer() throws Exception {
            when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
            when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customer);
            CustomerEntity updatedCustomer = customerService.updateCustomer(1L, customer);
            assertEquals(customer.getFirstName(), updatedCustomer.getFirstName());
        }

        @Test
        public void testDeleteCustomer() throws Exception {
            doNothing().when(customerRepository).deleteById(1L);
            customerService.deleteCustomer(1L);
            verify(customerRepository, times(1)).deleteById(1L);
        }
    }
