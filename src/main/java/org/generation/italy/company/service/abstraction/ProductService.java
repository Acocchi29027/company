package org.generation.italy.company.service.abstraction;

import org.generation.italy.company.dto.ProductDTO;
import org.generation.italy.company.model.Product;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Optional<Product> findById(int id);
    boolean deleteById(int id);
    Product create(Product p);
    List<Product> findByProductName(String name);
    List<Product> findByDiscontinued(Boolean discontinued);
    List<Product> findByProductNameAndIsDiscontinued(String name, Boolean discontinued);
    List<Product> findByCategoryName(String name);
    List<Product> findBySupplierCountry(String country);
    List<Product> findProductsAboveAveragePrice();
    List<Product> findProductsAboveCategoryAverage();
    List<Product> findProductsNeverOrdered();
    List<Product> findProductsSoldByEmployee(int empId);
    List<Product> findProductsNotOrderedSince(LocalDate date);
    List<Product> findAvailableProductsInPriceRange(double min, double max);

    boolean update(Product product);
}
