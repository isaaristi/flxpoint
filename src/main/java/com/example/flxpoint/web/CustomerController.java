package com.example.flxpoint.web;

import com.example.flxpoint.Service.CustomerService;
import com.example.flxpoint.Service.impl.CrmServiceImpl;
import com.example.flxpoint.data.CrmCustomer;
import com.example.flxpoint.data.CustomerEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CrmServiceImpl crmService;
    @Operation(summary = "Crear un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping
    public ResponseEntity<CustomerEntity> createCustomer(@RequestBody CustomerEntity customer) throws Exception {
        CustomerEntity savedCustomer = customerService.createCustomer(customer);
        crmService.syncCustomer(savedCustomer); // Sincroniza con el CRM
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    @Operation(summary = "Crear un nuevo cliente con reintento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping("/retry")
    public ResponseEntity<CustomerEntity> createCustomerRetry(@RequestBody CustomerEntity customer) throws Exception {
        CustomerEntity savedCustomer = customerService.createCustomer(customer);
        crmService.syncCustomerWithRetry(savedCustomer); // Sincroniza con el CRM
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }
    @Operation(summary = "Buscar un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerEntity> getCustomer(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(customerService.getCustomer(id));
    }

    @Operation(summary = "Lista de clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @GetMapping
    public ResponseEntity<List<CustomerEntity>> getCustomers() throws Exception {
        return ResponseEntity.ok(customerService.getCustomers());
    }
    @Operation(summary = "Actualizar un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente actualizado"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CustomerEntity> updateCustomer(@PathVariable Long id, @RequestBody CustomerEntity customer) throws Exception {
        CustomerEntity updatedCustomer = customerService.updateCustomer(id, customer);
        crmService.syncCustomer(updatedCustomer); // Sincroniza con el CRM
        return ResponseEntity.ok(customerService.updateCustomer(id, customer));
    }

    @Operation(summary = "Actualizar un nuevo cliente con reintento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente actualizado"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PutMapping("/{id}/retry")
    public ResponseEntity<CustomerEntity> updateCustomerRetry(@PathVariable Long id, @RequestBody CustomerEntity customer) throws Exception {
        CustomerEntity updatedCustomer = customerService.updateCustomer(id, customer);
        crmService.syncCustomerWithRetry(updatedCustomer); // Sincroniza con el CRM
        return ResponseEntity.ok(customerService.updateCustomer(id, customer));
    }

    @Operation(summary = "Eliminar un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente eliminado"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) throws Exception {
        customerService.deleteCustomer(id);
        crmService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Buscar CRM")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CRM encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @GetMapping("/crm/customers")
    public ResponseEntity<Collection<CrmCustomer>> getCrmCustomers() {
        Collection<CrmCustomer> customers = crmService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
}
