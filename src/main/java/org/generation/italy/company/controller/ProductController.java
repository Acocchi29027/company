package org.generation.italy.company.controller;

import org.generation.italy.company.dto.ProductDTO;
import org.generation.italy.company.dto.ProductSummaryDTO;
import org.generation.italy.company.model.Category;
import org.generation.italy.company.model.Product;
import org.generation.italy.company.service.abstraction.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.generation.italy.company.dto.ProductDTO.fromProduct;
import static org.generation.italy.company.dto.ProductSummaryDTO.summaryFromProduct;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductDTO> findProducts(@RequestParam(required = false) String categoryName,
                                         @RequestParam(required = false) String supplierCountry,
                                         @RequestParam(required = false) Boolean checkAveragePrice,
                                         @RequestParam(required = false) Boolean checkSameCategory,
                                         @RequestParam(required = false) String productName,
                                         @RequestParam(required = false) Boolean discontinued) {
        List<Product> products;
        if (categoryName != null) {
            products = service.findByCategoryName(categoryName);
        } else if (supplierCountry != null) {
            products = service.findBySupplierCountry(supplierCountry);
        } else if (checkAveragePrice && checkSameCategory == null) {
            products = service.findByUnitpriceGreaterThanAverageUnitprice();
        } else if (checkAveragePrice && checkSameCategory) {
            products = service.findByUnitpriceGreaterThanAverageUnitpriceAndSameCategory();
        } else if(productName == null && discontinued == null) {
            products = service.findAll();
        } else if (productName == null) {
            products = service.findByDiscontinued(discontinued);
        } else if (discontinued == null) {
            products = service.findByProductName(productName);
        } else {
            products = service.findByProductNameAndIsDiscontinued(productName, discontinued);
        }
        return products.stream().map(ProductDTO::fromProduct).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        Optional<Product> op = service.findById(id);
        if (op.isPresent()) {
            return ResponseEntity.ok(fromProduct(op.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        boolean deleted = service.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProductSummaryDTO> create(@RequestBody ProductSummaryDTO productSummaryDto) {
        Product product = productSummaryDto.toEntity();
        Product created = service.create(product);
        ProductSummaryDTO dto = summaryFromProduct(created);
        URI location = URI.create("/api/products/" + created.getProductId());
        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody ProductSummaryDTO productSummaryDTO){
        if ( id != productSummaryDTO.getProductId()){
            return ResponseEntity.badRequest().body("l'id del path deve coincidere con l'id dell'entity");
        }
        Product product = productSummaryDTO.toEntity();
        boolean updated = service.update(product);
        if (!updated){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(summaryFromProduct(product));
    }
}
