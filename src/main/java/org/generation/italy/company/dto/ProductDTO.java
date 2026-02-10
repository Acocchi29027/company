package org.generation.italy.company.dto;

import jakarta.persistence.*;
import org.generation.italy.company.model.BooleanToIntegerConverter;
import org.generation.italy.company.model.Category;
import org.generation.italy.company.model.Product;
import org.generation.italy.company.model.Supplier;

public class ProductDTO extends ProductSummaryDTO {
    private String supplierCompanyName;
    private String categoryName;

    public ProductDTO() {}

    public ProductDTO(Integer productId, String productName, Integer supplierId, String supplierCompanyName,
                      Integer categoryId, String categoryName, double unitprice, boolean discontinued) {
        super(productId,productName,supplierId,categoryId,unitprice,discontinued);
        this.supplierCompanyName = supplierCompanyName;
        this.categoryName = categoryName;

    }

    public static ProductDTO fromProduct(Product p) {
        return new ProductDTO(p.getProductId(), p.getProductName(), p.getSupplier().getSupplierId(),
                p.getSupplier().getCompanyName(), p.getCategory().getCategoryId(), p.getCategory().getCategoryname(),
                p.getUnitprice(), p.isDiscontinued());
    }

    @Override
    public Product toEntity() {
//        return new Product(productId, productName, new Supplier(supplierId, supplierCompanyName),
//                new Category(categoryId, categoryName, null), unitprice, discontinued);
        Product product = super.toEntity();
        product.getCategory().setCategoryname(categoryName);
        product.getSupplier().setCompanyName(supplierCompanyName);
        return product;
    }

    public String getSupplierCompanyName() {
        return supplierCompanyName;
    }

    public void setSupplierCompanyName(String supplierCompanyName) {
        this.supplierCompanyName = supplierCompanyName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
