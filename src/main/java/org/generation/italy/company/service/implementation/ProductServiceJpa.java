package org.generation.italy.company.service.implementation;

import org.generation.italy.company.model.Product;
import org.generation.italy.company.repository.abstraction.ProductRepository;
import org.generation.italy.company.service.abstraction.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceJpa implements ProductService {
    private ProductRepository repo;

    @Autowired
    public ProductServiceJpa(ProductRepository repo) {
        this.repo = repo;
        System.out.println("*************************************");
        System.out.println(repo.getClass().getName());
    }

    @Override
    public List<Product> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Product> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        Optional<Product> op = repo.findById(id);
        if (op.isPresent()) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Product create(Product p) {
        return repo.save(p);
    }

    @Override
    public List<Product> findByProductName(String name) {
        return repo.findByProductNameContaining(name);
    }

    @Override
    public List<Product> findByDiscontinued(Boolean discontinued) {
        return repo.findByDiscontinued(discontinued);
    }

    @Override
    public List<Product> findByProductNameAndIsDiscontinued(String name, Boolean discontinued) {
        return repo.findByProductNameAndIsDiscontinued(name, discontinued);
    }

    @Override
    public List<Product> findByCategoryName(String name) {
        return repo.findByCategoryName(name);
    }

    @Override
    public List<Product> findBySupplierCountry(String country) {
        return repo.findBySupplierCountry(country);
    }

    @Override
    public List<Product> findProductsAboveAveragePrice() {
        return repo.findProductsAboveAveragePrice();
    }

    @Override
    public List<Product> findProductsAboveCategoryAverage() {
        return repo.findProductsAboveCategoryAverage();
    }

    @Override
    public List<Product> findProductsNeverOrdered() {
        return repo.findProductsNeverOrdered();
    }

    @Override
    public List<Product> findProductsSoldByEmployee(int empId) {
        return repo.findProductsSoldByEmployee(empId);
    }

    @Override
    public List<Product> findProductsNotOrderedSince(LocalDate date) {
        return repo.findProductsNotOrderedSince(date);
    }

    @Override
    public List<Product> findAvailableProductsInPriceRange(double min, double max) {
        return repo.findAvailableProductsInPriceRange(min, max);
    }

    @Override
    public boolean update(Product product) {
        if ( !repo.existsById(product.getProductId())){
            return false;
        }
        repo.save(product);
        return true;
    }
}
