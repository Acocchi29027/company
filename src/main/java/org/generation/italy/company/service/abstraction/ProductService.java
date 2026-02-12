package org.generation.italy.company.service.abstraction;

import org.generation.italy.company.dto.ProductDTO;
import org.generation.italy.company.model.Product;

import org.springframework.data.domain.Pageable;
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

    boolean update(Product product);
    // 1. Ritorna i prodotti di una categoria dato il nome categoria
    List<ProductDTO> findByCategoryCategoryName(String name);
    // 2. Ritorna i prodotti di supplier che vivono in una country data
    List<ProductDTO> findBySupplierCountry(String country);
    // 3. Ritorna i prodotti con prezzo > prezzo medio di tutti i prodotti
    List<ProductDTO> findProductsCostingMoreThanAverage();
    // 4. Ritorna i prodotti con prezzo > media dei prodotti della stessa categoria
    List<ProductDTO> findProductsCostingMoreThanCategoryAverage();
    // 5. Ritorna i prodotti mai ordinati
    List<ProductDTO> findProductsNeverOrdered();
    // 6. Ritorna i 3 prodotti più ordinati
    List<ProductDTO> findMostFrequentlyOrderedProducts(Pageable pageable);
    // 7. Ritorna i prodotti la cui vendita è stata seguita da un impiegato
    List<ProductDTO> findProductsByEmployee(Integer employeeId);
    // 8. Ritorna i prodotti non ordinati a partire da una certa data
    List<ProductDTO> findProductsNotOrderedSince(LocalDate since);
    // 9. Assegnare ad un Product (id) un nuovo Supplier (id)
    ProductDTO changeSupplier(Integer productId, Integer supplierId);
}
