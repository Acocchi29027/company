package org.generation.italy.company.controller;

import org.generation.italy.company.dto.ProductDTO;
import org.generation.italy.company.dto.ProductSummaryDTO;
import org.generation.italy.company.model.Category;
import org.generation.italy.company.model.Product;
import org.generation.italy.company.service.abstraction.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.generation.italy.company.dto.ProductDTO.fromProduct;
import static org.generation.italy.company.dto.ProductSummaryDTO.summaryFromProduct;

@RestController
@RequestMapping("/api/products")
public class
ProductController {
    private ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductDTO> findProducts(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) Boolean discontinued) {
//        return List.of(new Product(2, " ", null, new Category(1, "Beverages",
//                        "Beviamoci sopra"), 100, false),
//                new Product(3, " ", null, new Category(1, "Beverages", "Beviamoci sopra"), 100,
//                        true)
//                );
        List<Product> products;
        if (name == null && discontinued == null) {
            products = service.findAll();
        } else if (name == null) {
            products = service.findByDiscontinued(discontinued);
        } else if (discontinued == null) {
            products = service.findByProductName(name);
        } else {
            products = service.findByProductNameAndIsDiscontinued(name, discontinued);
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
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody ProductSummaryDTO productSummaryDTO) {
        if (id != productSummaryDTO.getProductId()) {
            return ResponseEntity.badRequest().body("l'id del path deve coincidere con l'id dell'entity");
        }
        Product product = productSummaryDTO.toEntity();
        boolean updated = service.update(product);
        if (!updated) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(summaryFromProduct(product));
    }

    // 1 es. - Prodotti di una categoria dato il nome categoria
    @GetMapping("/find/category/categoryname")
    public ResponseEntity<List<ProductDTO>> findByCategoryName(@RequestParam String categoryname) {

        if (categoryname == null || categoryname.isBlank()) {
            return ResponseEntity.badRequest().build();
            // 400 Bad Request: il client ha inviato una richiesta non valida (parametro mancante/vuoto)
            // build() crea la ResponseEntity "senza body" (solo status code)
        }

        return ResponseEntity.ok(service.findByCategoryCategoryName(categoryname)); // Body + Status code 200
    }

    // 2 es. - Prodotti di supplier che vivono in una country data
    @GetMapping("/find/supplier/country")
    public ResponseEntity<List<ProductDTO>> findBySupplierCountry(@RequestParam String country) {
        if (country == null || country.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.findBySupplierCountry(country));
    }

    // 3 es. - Prodotti che costano più del costo medio di tutti i prodotti
    @GetMapping("/find/products/avg")
    public ResponseEntity<List<ProductDTO>> findProductsCostingMoreThanAVG() {
        List<ProductDTO> res = service.findProductsCostingMoreThanAverage();
        return res.isEmpty() // Verifico se è vuota
                ? ResponseEntity.noContent().build() // se è vuota 204
                : ResponseEntity.ok(res);
    }

    // 4 es. - Prodotti che costano più della media dei prodotti della stessa categoria
    @GetMapping("/find/products/avg/category")
    public ResponseEntity<List<ProductDTO>> findProductsCostingMoreThanCategoryAverage() {
        List<ProductDTO> res = service.findProductsCostingMoreThanCategoryAverage();
        return res.isEmpty() // Verifico se è vuota
                ? ResponseEntity.noContent().build() // se è vuota 204
                : ResponseEntity.ok(res);
    }

    // 5 es. - Prodotti mai ordinati
    @GetMapping("/find/products/orderdetails")
    public ResponseEntity<List<ProductDTO>> findProductsNeverOrdered() {
        List<ProductDTO> res = service.findProductsNeverOrdered();
        return res.isEmpty() // Verifico se è vuota
                ? ResponseEntity.noContent().build() // se è vuota 204
                : ResponseEntity.ok(res);
    }

    // 6 es. - I 3 prodotti più ordinati
    @GetMapping("/find/products/orderdetails/top")
    public ResponseEntity<List<ProductDTO>> findMostFrequentlyOrderedProducts() {

        Pageable pageable = PageRequest.of(0, 3);
        List<ProductDTO> res = service.findMostFrequentlyOrderedProducts(pageable);

        return res.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(res);
    }


    // 7 es. - Prodotti la cui vendita è seguita da un impiegato dato
    @GetMapping("/by-employee/{employeeId}")
    public ResponseEntity<List<ProductDTO>> findProductsByEmployee(@PathVariable Integer employeeId) {
        return ResponseEntity.ok(service.findProductsByEmployee(employeeId));
    }

    // 8 es. - Prodotti non ordinati a partire da una certa data nel passato
    @GetMapping("/not-ordered-since")
    public ResponseEntity<List<ProductDTO>> findProductsNotOrderedSince(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate since
    ) {
        return ResponseEntity.ok(service.findProductsNotOrderedSince(since));
    }

    // 9 es. - Assegnare a un prodotto (id) un nuovo supplier (id)
    @PutMapping("/{productId}/supplier/{supplierId}")
    public ResponseEntity<ProductDTO> changeSupplier(
            @PathVariable Integer productId,
            @PathVariable Integer supplierId
    ) {
        return ResponseEntity.ok(service.changeSupplier(productId, supplierId));
    }
}
