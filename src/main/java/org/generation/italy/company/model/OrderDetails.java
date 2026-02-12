package org.generation.italy.company.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orderdetails")
public class OrderDetails {
    @EmbeddedId
    private OrderDetailsId id;

    @ManyToOne
    @JoinColumn(name = "orderid")
    @MapsId("orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productid")
    @MapsId("productId")
    private Product product;

    @Column(columnDefinition = "NUMERIC", name = "unitprice")
    private double unitprice;
    private Integer qty;
    @Column(columnDefinition = "NUMERIC", name = "discount")
    private double discount;

    public OrderDetails(){}

    public OrderDetails(OrderDetailsId id, Order order, Product product, double unitprice, Integer qty, double discount) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.unitprice = unitprice;
        this.qty = qty;
        this.discount = discount;
    }

    public OrderDetailsId getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public Integer getQty() {
        return qty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setId(OrderDetailsId id) {
        this.id = id;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
