package org.generation.italy.company.service.implementation;

import jakarta.transaction.Transactional;
import org.generation.italy.company.model.Product;
import org.generation.italy.company.model.Supplier;
import org.generation.italy.company.repository.abstraction.ProductRepository;
import org.generation.italy.company.repository.abstraction.SupplierRepository;
import org.generation.italy.company.service.abstraction.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceJpa implements ProductService {
    private ProductRepository productRepo;
    private SupplierRepository supplierRepo;

    @Autowired
    public ProductServiceJpa(ProductRepository productRepo, SupplierRepository supplierRepo) {
        this.productRepo = productRepo;
        this.supplierRepo = supplierRepo;
        System.out.println("*************************************");
        System.out.println(productRepo.getClass().getName());
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Optional<Product> findById(int id) {
        return productRepo.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        Optional<Product> op = productRepo.findById(id);
        if (op.isPresent()) {
            productRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Product create(Product p) {
        return productRepo.save(p);
    }

    @Override
    public List<Product> findByProductName(String name) {
        return productRepo.findByProductNameContaining(name);
    }

    @Override
    public List<Product> findByDiscontinued(Boolean discontinued) {
        return productRepo.findByDiscontinued(discontinued);
    }

    @Override
    public List<Product> findByProductNameAndIsDiscontinued(String name, Boolean discontinued) {
        return productRepo.findByProductNameAndIsDiscontinued(name, discontinued);
    }

    @Override
    public boolean update(Product product) {
        if ( !productRepo.existsById(product.getProductId())){
            return false;
        }
        productRepo.save(product);
        return true;
    }

    @Transactional
    @Override
    public boolean updateProductSupplier(int productId, int supplierId) {
        Optional<Product> op = productRepo.findById(productId);
        if (op.isEmpty()) {
            return false;
        }
        Product p = op.get();
        Optional<Supplier> os = supplierRepo.findById(supplierId);
        if(os.isEmpty()) {
            return false;
        }
        p.setSupplier(os.get());
        return true;
    }
}
