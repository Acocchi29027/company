package org.generation.italy.company.dto;

import org.generation.italy.company.model.Customer;

public class CustomerSummaryDTO {
    private Integer custId;
    private String companyName;
    private String address;
    private String city;
    private String country;
    private String phone;

    public CustomerSummaryDTO(Integer custId, String companyName, String address, String city, String country,
                              String phone) {
        this.custId = custId;
        this.companyName = companyName;
        this.address = address;
        this.city = city;
        this.country = country;
        this.phone = phone;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static CustomerSummaryDTO fromCustomer(Customer c) {
        return new CustomerSummaryDTO(c.getCustId(), c.getCompanyName(), c.getAddress(), c.getCity(), c.getCountry(),
                c.getPhone());
    }

    public Customer toEntity() {
        return new Customer(custId, companyName, null, null, address, city, null, null, country, phone, null);
    }
}
