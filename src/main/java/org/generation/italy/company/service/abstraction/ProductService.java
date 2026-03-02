package org.generation.italy.company.service.abstraction;

import org.generation.italy.company.dto.ProductDTO;
import org.generation.italy.company.model.Product;
import org.springframework.data.domain.Pageable;

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
    boolean update(Product product);
    boolean updateProductSupplier(int productId, int supplierId);
    List<ProductDTO> findMostFrequentlyOrderedProducts(Pageable pageable);
    List<ProductDTO> findByCategoryName(String name);
    List<ProductDTO> findBySupplierCountry(String country);
    List<ProductDTO> findProductsCostingMoreThanAverage();
    List<ProductDTO> findProductsCostingMoreThanCategoryAverage();
    List<ProductDTO> findProductsNeverOrdered();
    List<ProductDTO> findProductsByEmployee(Integer employeeId);

    List<ProductDTO> findProductsNotOrderedSince(LocalDate since);

    ProductDTO changeSupplier(Integer productId, Integer supplierId);
}
