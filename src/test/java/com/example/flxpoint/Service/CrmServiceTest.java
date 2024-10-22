package com.example.flxpoint.Service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.flxpoint.Service.impl.CrmServiceImpl;
import com.example.flxpoint.data.CrmCustomer;
import com.example.flxpoint.data.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CrmServiceTest {
    private Map<Long, CrmCustomer> crmDatabase = new HashMap<>();

        @InjectMocks
        private CrmServiceImpl crmService;

        private CrmCustomer crmCustomer;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
            crmCustomer = new CrmCustomer();
            crmCustomer.setId(1L);
            crmCustomer.setFullName("John Doe");
            crmCustomer.setContactEmail("john.doe@example.com");
            crmCustomer.setPrimaryPhone("123456789");
            crmCustomer.setLocation("123 Main St, Springfield, IL, 62704");
        }

        @Test
        public void testSyncCustomer() throws Exception {
            when(crmDatabase.put(1L,any(CrmCustomer.class))).thenReturn(crmCustomer);
            crmService.syncCustomer(new CustomerEntity());
            verify(crmDatabase, times(1)).put(1L,crmCustomer);
        }

        @Test
        public void testGetAllCustomers() {
            when(crmDatabase.values()).thenReturn((Collection<CrmCustomer>) crmCustomer);
            verify(crmDatabase, times(1)).values();
        }


}
