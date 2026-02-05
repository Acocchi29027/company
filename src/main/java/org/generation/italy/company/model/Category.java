package org.generation.italy.company.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryid")
    private Integer categoryId;
    @Column(name = "categoryname", nullable = false)
    private String categoryName;
    @Column(name = "description", nullable = false)
    private String description;

    public Category(){}

    public Category(Integer categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.categoryName = name;
        this.description = description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryname() {
        return categoryName;
    }

    public void setCategoryname(String categoryname) {
        this.categoryName = categoryname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

/**
 * Creare un repository per i prodotti che avrà i seguenti metodi:
 * findByID di int id
 * findBySupplierID input supplier ID
 * findBySupplierName input String companyname -> tutti i prodotti
 * findByCategoryName
 * deleteProductByID input int id
 * updateProduct input product p
 * ogni volta che i metodi ritornano uno o più prodotti, gli oggetti prodotti che
 * vengono ritornati devono avere settati il loro oggetto category
 */

