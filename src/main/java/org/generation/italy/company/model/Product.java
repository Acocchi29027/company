package org.generation.italy.company.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productid")
    private Integer productId;
    @Column(name = "productname")
    private String productName;
    @ManyToOne
    @JoinColumn(name = "supplierid")
    private Supplier supplier;
    @ManyToOne
    @JoinColumn(name = "categoryid")
    private Category category;
    @Column(columnDefinition = "NUMERIC", name = "unitprice")
    private double unitprice;
    @Convert(converter = BooleanToIntegerConverter.class)
    @Column(name = "discontinued")
    private boolean discontinued;

    public Product(){}

    public Product(Integer productId, String productName, Supplier supplier, Category category, double unitprice, boolean discontinued) {
        this.productId = productId;
        this.productName = productName;
        this.supplier = supplier;
        this.category = category;
        this.unitprice = unitprice;
        this.discontinued = discontinued;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
    //query che fa una join fra prodotti e categorie
    //in modo da leggere il prodotto con la categoria
}
/**
 *  * Creare un repository per i prodotti che avrà i seguenti metodi:
 *  * findByID di int id
 *  * findBySupplierID input supplier ID
 *  * findBySupplierName input String companyname -> tutti i prodotti
 *  * findByCategoryName
 *  * deleteProductByID input int id
 *  * updateProduct input product p
 *  * ogni volta che i metodi ritornano uno o più prodotti, gli oggetti prodotti che
 *  * vengono ritornati devono avere settati il loro oggetto category
 *  */


