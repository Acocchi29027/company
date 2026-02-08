package org.generation.italy.company.controller;

import org.generation.italy.company.dto.CustomerDTO;
import org.generation.italy.company.model.Customer;
import org.generation.italy.company.service.abstraction.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

    //    @GetMapping("/{id}")
//    public ResponseEntity<?> findById(@PathVariable int id) {
//        Optional<Product> op = service.findById(id);
//        if (op.isPresent()) {
//            return ResponseEntity.ok(fromProduct(op.get()));
//        }
//        return ResponseEntity.notFound().build();
//    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        Optional<Customer> op = service.findById(id);
        if (op.isPresent()) {
            return ResponseEntity.ok(CustomerDTO.summaryFromCustomer(op.get())); // se togliessi CSDTO otterei direttamente il customer
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//    @PostMapping
//    public ResponseEntity<ProductSummaryDTO> create(@RequestBody ProductSummaryDTO productSummaryDto) {
//        Product product = productSummaryDto.toEntity();
//        Product created = service.create(product);
//        ProductSummaryDTO dto = summaryFromProduct(product);
//        URI location = URI.create("/api/products/" + created.getProductId());
//        return ResponseEntity.created(location).body(dto);
//    }
    @PostMapping
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerDTO customerDTO){
        Customer customer = customerDTO.toEntity();
        Customer cCreated = service.create(customer);
        CustomerDTO dto = CustomerDTO.summaryFromCustomer(customer);
        URI location = URI.create("/api/customers/" + cCreated.getCustId());
        return ResponseEntity.created(location).body(dto);
    }
}


