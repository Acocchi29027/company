package org.generation.italy.company.service.abstraction;

import org.generation.italy.company.dto.ProductDTO;
import org.generation.italy.company.model.Employee;
import org.generation.italy.company.model.Product;

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
    boolean update(Product product);
    List<Product> findByCategoryName(String name);
    List<Product> findBySupplierCountry(String country);
    List<Product> findByUnitpriceGreaterThanAverageUnitprice();
    List<Product> findByUnitpriceGreaterThanAverageUnitpriceAndSameCategory();
    List<Product> findByNoOrders();
    List<Product> findByThreeMostOrdered();
    List<Product> findByOrderEmployee(Employee e);
}
