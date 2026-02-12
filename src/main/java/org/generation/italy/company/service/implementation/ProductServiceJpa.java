package org.generation.italy.company.service.implementation;

import org.generation.italy.company.dto.ProductDTO;
import org.generation.italy.company.model.Category;
import org.generation.italy.company.model.Product;
import org.generation.italy.company.model.Supplier;
import org.generation.italy.company.repository.abstraction.ProductRepository;
import org.generation.italy.company.repository.abstraction.SupplierRepository;
import org.generation.italy.company.service.abstraction.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductServiceJpa implements ProductService {
    private ProductRepository repo;
    private final SupplierRepository supplierRepo;
    // Constructor injection: Spring inietta i repository (bean) necessari al service
    // per leggere/salvare Product e recuperare Supplier durante changeSupplier().

    @Autowired
    public ProductServiceJpa(ProductRepository repo, SupplierRepository supplierRepo) {
        this.repo = repo;
        this.supplierRepo = supplierRepo;
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
    public boolean update(Product product) {
        if (!repo.existsById(product.getProductId())) {
            return false;
        }
        repo.save(product);
        return true;
    }

    // 1 es
    @Override
    public List<ProductDTO> findByCategoryCategoryName(String name) {
        return repo.findByCategoryCategoryName(name)
                .stream()
                .map(ProductDTO::fromProduct)
                .toList();
    }

    // 2 es
    @Override
    public List<ProductDTO> findBySupplierCountry(String country) {
        return repo.findBySupplierCountry(country)
                .stream()
                .map(ProductDTO::fromProduct)
                .toList();
    }

    // 3 es
    @Override
    public List<ProductDTO> findProductsCostingMoreThanAverage() {
        return repo.findProductsCostingMoreThanAverage()
                .stream()
                .map(ProductDTO::fromProduct)
                .toList();
    }

    // 4 es
    @Override
    public List<ProductDTO> findProductsCostingMoreThanCategoryAverage() {
        return repo.findProductsCostingMoreThanCategoryAverage()
                .stream()
                .map(ProductDTO::fromProduct)
                .toList();
    }

    // 5 es
    @Override
    public List<ProductDTO> findProductsNeverOrdered() {
        return repo.findProductsNeverOrdered()
                .stream()
                .map(ProductDTO::fromProduct)
                .toList();
    }

    // 6 es
    @Override
    public List<ProductDTO> findMostFrequentlyOrderedProducts(Pageable pageable) {
        return repo.findMostFrequentlyOrderedProducts(pageable)
                .stream()
                .map(ProductDTO::fromProduct)
                .toList();
    }

    // 7 es
    @Override
    public List<ProductDTO> findProductsByEmployee(Integer employeeId) {
        return repo.findProductsByEmployee(employeeId)
                .stream()
                .map(ProductDTO::fromProduct)
                .toList();
    }

    // 8 es
    public List<ProductDTO> findProductsNotOrderedSince(LocalDate since) {
        return repo.findProductsNotOrderedSince(since.atStartOfDay())
                .stream()
                .map(ProductDTO::fromProduct)
                .toList();
    }
    // 9 es

    @Override
    public ProductDTO changeSupplier(Integer productId, Integer supplierId) {

        Product p = repo.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found: " + productId)); // RuntimeException che dice: non esiste un elemento disponibile” / “non trovato

        Supplier s = supplierRepo.findById(supplierId)
                .orElseThrow(() -> new NoSuchElementException("Supplier not found: " + supplierId));

        p.setSupplier(s);

        Product saved = repo.save(p);

        return ProductDTO.fromProduct(saved);
    }

    /**
     * ES 9 - Change supplier:
     * // Per assegnare a un Product un nuovo supplier partendo da supplierId, serve un oggetto Supplier (non basta l’id).
     * // Quindi creiamo SupplierRepository e lo iniettiamo nel service per recuperare il Supplier dal DB (findById),
     * // poi facciamo p.setSupplier(s) e salviamo il Product (repo.save).
     */


}
