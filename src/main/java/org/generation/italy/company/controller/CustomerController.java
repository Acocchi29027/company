package org.generation.italy.company.controller;

import org.generation.italy.company.model.Customer;
import org.generation.italy.company.service.abstraction.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/customers")
public class CustomerController {
  private CustomerService service;
  @Autowired

    public CustomerController(CustomerService service) {
       this.service = service;
      System.out.println("****************AVVENUTA INIEZIONE*********************");
    }
    @GetMapping
    public List <Customer> findCustomers(@RequestParam(required = false)String companyName){
      List<Customer> customers;
      if(companyName == null){
          System.out.println("Non esiste un customer con questo id");
      } else {
          customers = service.findByCompanyName(companyName);
      }
    }
}
