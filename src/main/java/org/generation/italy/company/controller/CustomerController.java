package org.generation.italy.company.controller;

import org.generation.italy.company.dto.CustomerDTO;
import org.generation.italy.company.dto.CustomerSummaryDTO;
import org.generation.italy.company.model.Customer;
import org.generation.italy.company.service.abstraction.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.generation.italy.company.dto.CustomerSummaryDTO.summaryFromCustomer;
import static org.generation.italy.company.dto.ProductSummaryDTO.summaryFromProduct;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public List<CustomerDTO> findAll(){
       List<Customer> customers = service.findAll();
      return customers.stream().map(CustomerDTO::fromCustomer).toList();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        Optional<Customer> customer = service.findById(id);
        if(customer.isPresent()){
            return ResponseEntity.ok(customer.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<CustomerSummaryDTO> create(@RequestBody CustomerSummaryDTO customerSummaryDTO){
        Customer customer = customerSummaryDTO.toEntity();
        Customer created = service.create(customer);
        CustomerSummaryDTO dto = summaryFromCustomer(created);
        URI location = URI.create("/api/customers/" + created.getCustId());
        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody CustomerSummaryDTO customerSummaryDTO){
        if (id != customerSummaryDTO.getCustId()){
            return ResponseEntity.badRequest().build();
        }
        Customer customer = customerSummaryDTO.toEntity();
        boolean updated = service.update(customer);
        if (!updated){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(summaryFromCustomer(customer));
    }
}
