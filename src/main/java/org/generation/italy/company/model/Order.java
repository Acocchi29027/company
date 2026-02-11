package org.generation.italy.company.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid")
    private Integer orderId;
    @ManyToOne
    @JoinColumn(name = "custid")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "empid")
    private Employee employee;
    @Column(name = "orderdate")
    private LocalDateTime orderDate;
    @Column(name = "requireddate")
    private LocalDateTime requiredDate;
    @Column(name = "shippeddate")
    private LocalDateTime shippedDate;
    @Column(columnDefinition = "NUMERIC", name = "freight")
    private double freight;
    @Column(name = "shipname")
    private String shipName;
    @Column(name = "shipaddress")
    private String shipAddress;
    @Column(name = "shipcity")
    private String shipCity;
    @Column(name = "shipregion")
    private String shipRegion;
    @Column(name = "shippostalcode")
    private String shipPostalCode;
    @Column(name = "shipcountry")
    private String shipCountry;

    public Order(){}

    public Order(Integer orderId, Customer customer, Employee employee, LocalDateTime orderDate, LocalDateTime requiredDate,
                 LocalDateTime shippedDate, double freight, String shipName, String shipAddress, String shipCity,
                 String shipRegion, String shipPostalCode, String shipCountry) {
        this.orderId = orderId;
        this.customer = customer;
        this.employee = employee;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.freight = freight;
        this.shipName = shipName;
        this.shipAddress = shipAddress;
        this.shipCity = shipCity;
        this.shipRegion = shipRegion;
        this.shipPostalCode = shipPostalCode;
        this.shipCountry = shipCountry;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public LocalDateTime getRequiredDate() {
        return requiredDate;
    }

    public LocalDateTime getShippedDate() {
        return shippedDate;
    }

    public double getFreight() {
        return freight;
    }

    public String getShipName() {
        return shipName;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public String getShipCity() {
        return shipCity;
    }

    public String getShipCountry() {
        return shipCountry;
    }

    public String getShipPostalCode() {
        return shipPostalCode;
    }

    public String getShipRegion() {
        return shipRegion;
    }
}
