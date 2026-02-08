package org.generation.italy.company.controller;

import org.generation.italy.company.dto.CustomerDTO;
import org.generation.italy.company.dto.ProductSummaryDTO;
import org.generation.italy.company.model.Customer;
import org.generation.italy.company.model.Product;
import org.generation.italy.company.service.abstraction.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.generation.italy.company.dto.ProductSummaryDTO.summaryFromProduct;

@RestController
@RequestMapping("api/customers")
public class CustomerController {
    private CustomerService service;

    @Autowired

    public CustomerController(CustomerService service) {
        this.service = service;
        System.out.println("****************AVVENUTA INIEZIONE DEL SERVICE*********************");
    }

    @GetMapping
    public List<CustomerDTO> findCustomers(@RequestParam(required = false) String companyName) {
        List<Customer> customers;
        if (companyName == null) {
            customers = List.of();
        } else {
            customers = service.findByCompanyName(companyName);
        }
        return customers.stream().map(CustomerDTO::summaryFromCustomer).toList();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        Optional<Customer> op = service.findById(id);
        if (op.isPresent()) {
            return ResponseEntity.ok(CustomerDTO.summaryFromCustomer(op.get())); // se togliessi CSDTO otterei direttamente il customer
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id){
        boolean deleted = service.deleteById(id);
        if(deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerDTO customerDTO){
        Customer customer = customerDTO.toEntity();
        Customer cCreated = service.create(customer);
        CustomerDTO dto = CustomerDTO.summaryFromCustomer(customer);
        URI location = URI.create("/api/customers/" + cCreated.getCustId());
        return ResponseEntity.created(location).body(dto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id,@RequestBody CustomerDTO customerDTO){
        if(id != customerDTO.getCustId()){
            return ResponseEntity.badRequest().body("l'id inserito non ha corrispondenze");
        }
        Customer c = customerDTO.toEntity();
        boolean updated = service.update(c);
        if(!updated){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(CustomerDTO.summaryFromCustomer(c));
    }
}


