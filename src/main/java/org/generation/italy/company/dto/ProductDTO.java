package org.generation.italy.company.dto;

import jakarta.persistence.*;
import org.generation.italy.company.model.BooleanToIntegerConverter;
import org.generation.italy.company.model.Category;
import org.generation.italy.company.model.Product;
import org.generation.italy.company.model.Supplier;

public class ProductDTO {
    private Integer productId;
    private String productName;
    private Integer supplierId;
    private String supplierCompanyName;
    private Integer categoryId;
    private String categoryName;
    private double unitprice;
    private boolean discontinued;

    public ProductDTO() {}

    public ProductDTO(Integer productId, String productName, Integer supplierId, String supplierCompanyName,
                      Integer categoryId, String categoryName, double unitprice, boolean discontinued) {
        this.productId = productId;
        this.productName = productName;
        this.supplierId = supplierId;
        this.supplierCompanyName = supplierCompanyName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.unitprice = unitprice;
        this.discontinued = discontinued;
    }

    public static ProductDTO fromProduct(Product p) {
        return new ProductDTO(p.getProductId(), p.getProductName(), p.getSupplier().getSupplierId(),
                p.getSupplier().getCompanyName(), p.getCategory().getCategoryId(), p.getCategory().getCategoryname(),
                p.getUnitprice(), p.isDiscontinued());
    }

    public Product toEntity() {
        return new Product(productId, productName, new Supplier(supplierId, supplierCompanyName),
                new Category(categoryId, categoryName, null), unitprice, discontinued);
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierCompanyName() {
        return supplierCompanyName;
    }

    public void setSupplierCompanyName(String supplierCompanyName) {
        this.supplierCompanyName = supplierCompanyName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }
}
