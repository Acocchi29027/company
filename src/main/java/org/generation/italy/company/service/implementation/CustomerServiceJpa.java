package org.generation.italy.company.service.implementation;

import org.generation.italy.company.model.Customer;
import org.generation.italy.company.repository.abstraction.CustomerRepository;
import org.generation.italy.company.service.abstraction.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CustomerServiceJPA implements CustomerService {
    private CustomerRepository repo;
    @Autowired
    public CustomerServiceJPA(CustomerRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Customer> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Customer> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        Optional<Customer> c = repo.findById(id);
        if (c.isPresent()) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Customer create(Customer c) {
        return repo.save(c);
    }

    @Override
    public List<Customer> findByCompanyName(String name) {
        return repo.findByCompanyName(name);
    }

    @Override
    public List<Customer> findByCity(String city) {
        return repo.findByCity(city);
    }

    @Override
    public List<Customer> findByCompanyNameAndByRegion(String companyname, String region) {
        return repo.findByCompanyNameAndByRegion(companyname,region);
    }

    @Override
    public List<Customer> findByPostalCode(String postalcode) {
        return repo.findByPostalCode(postalcode);
    }

    @Override
    public boolean update(Customer c) {
        if (!repo.existsById(c.getCustId())){
            return false;
        }
        repo.save(c);
        return true;
    }
}
