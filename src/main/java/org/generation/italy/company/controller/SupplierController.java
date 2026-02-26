package org.generation.italy.company.controller;

import org.generation.italy.company.dto.SupplierMinimalDTO;
import org.generation.italy.company.model.Supplier;
import org.generation.italy.company.service.abstraction.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    private ProductService service;

    @Autowired
    public SupplierController(ProductService productService) {
        this.service = productService;
    }

    @GetMapping("/minimal")
    public ResponseEntity<List<SupplierMinimalDTO>> getSupplierMinimals(){
        List<Supplier> suppliers = service.getSupplierMinimals();
        var dtos = suppliers.stream().map(SupplierMinimalDTO::minimalFromSupplier).toList();
        return ResponseEntity.ok(dtos);
    }
}
