package org.generation.italy.company.service.implementation;

import org.generation.italy.company.model.Customer;
import org.generation.italy.company.repository.abstraction.CustomerRepository;
import org.generation.italy.company.service.abstraction.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CustomerServiceJpa  implements CustomerService {
//facciamo un costruttore per iniettare il repository
    private CustomerRepository repo;

    @Autowired
    public CustomerServiceJpa(CustomerRepository repo) {
        this.repo = repo;
        System.out.println("*************************************");
        System.out.println(repo.getClass().getName());
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
        Optional<Customer> cs = repo.findById(id);
        if(cs.isPresent()){
            repo.deleteById(id);
            return  true;
        }
        return false ;
    }

    @Override
    public Customer create(Customer c) {
        return repo.save(c);
    }

    @Override
    public List<Customer> findByCompanyName(String companyName) {
        return repo.findByCompanyname(companyName);
    }

    @Override
    public List<Customer> findByCity(String city) {
        return repo.findByCity(city);
    }

    @Override
    public List<Customer> findByCompanyNameAndByRegion(String companyname, String region) {
        return repo.findByCompanyNameAndRegion(companyname, region);
    }

    @Override
    public List<Customer> findByPostalCode(String postalcode) {
        return repo.findByPostalCode(postalcode);
    }

    @Override
    public boolean update(Customer c) {
        if (!repo.existsById(c.getCustId())) {
            return false;
        }
        repo.save(c);
        return true;
    }
}
