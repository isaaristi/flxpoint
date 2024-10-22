package com.example.flxpoint.Controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.flxpoint.Service.CustomerService;
import com.example.flxpoint.Service.impl.CustomerServiceImpl;
import com.example.flxpoint.data.CustomerEntity;
import com.example.flxpoint.web.CustomerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class CustomerControllerTest {

        @Mock
        private CustomerServiceImpl customerService;

        @InjectMocks
        private CustomerController customerController;

        @Autowired
        private MockMvc mockMvc;

        private CustomerEntity customer;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
            mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

            customer = new CustomerEntity();
            customer.setCustomerId(1L);
            customer.setFirstName("John");
            customer.setLastName("Doe");
            customer.setEmail("john.doe@example.com");
            customer.setPhoneNumber("123456789");
        }

        @Test
        public void testCreateCustomer() throws Exception {
            when(customerService.createCustomer(any(CustomerEntity.class))).thenReturn(customer);

            mockMvc.perform(post("/api/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\", \"phoneNumber\": \"123456789\" }"))
                    .andExpect(status().isCreated());
        }

        @Test
        public void testGetCustomer() throws Exception {
            when(customerService.getCustomer(1L)).thenReturn(customer);

            mockMvc.perform(get("/api/customers/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName").value("John"));
        }

        @Test
        public void testUpdateCustomer() throws Exception {
            when(customerService.updateCustomer(eq(1L), any(CustomerEntity.class))).thenReturn(customer);

            mockMvc.perform(put("/api/customers/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\", \"phoneNumber\": \"123456789\" }"))
                    .andExpect(status().isOk());
        }

        @Test
        public void testDeleteCustomer() throws Exception {
            doNothing().when(customerService).deleteCustomer(1L);

            mockMvc.perform(delete("/api/customers/1"))
                    .andExpect(status().isNoContent());
        }
    }
