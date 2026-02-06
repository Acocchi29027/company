package org.generation.italy.company.dto;

import org.generation.italy.company.model.Category;
import org.generation.italy.company.model.Product;
import org.generation.italy.company.model.Supplier;

public class ProductSummaryDTO {
    protected Integer productId;
    protected String productName;
    protected Integer supplierId;
    protected Integer categoryId;
    protected double unitprice;
    protected boolean discontinued;

    public ProductSummaryDTO(){}

    public ProductSummaryDTO(Integer productId, String productName, Integer supplierId, Integer categoryId, double unitprice, boolean discontinued) {
        this.productId = productId;
        this.productName = productName;
        this.supplierId = supplierId;
        this.categoryId = categoryId;
        this.unitprice = unitprice;
        this.discontinued = discontinued;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public Product toEntity(){
        return new Product(productId, productName, new Supplier(supplierId, null),
                new Category(categoryId, null, null), unitprice, discontinued);
    }
}
