package org.generation.italy.company.dto;

import jakarta.persistence.Column;
import org.generation.italy.company.model.Customer;

public class CustomerDTO {

    private Integer custId;
    private String companyName;
    private String contactName;
    private String contactTitle;
    private String address;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private String phone;
    private String fax;

    public CustomerDTO(){}

    public CustomerDTO(Integer custId, String companyName, String contactName, String contactTitle, String address,
                       String city, String region, String postalCode, String country, String phone, String fax){

        this.custId = custId;
        this.companyName = companyName;
        this.contactName = contactName;
        this.contactTitle = contactTitle;
        this.address = address;
        this.city = city;
        this.region = region;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
        this.fax = fax;
    }

    public Customer toEntity() {
        Customer customer = new Customer(custId, companyName, contactName, contactTitle, address,
                city, region, postalCode, country, phone, fax);
        return customer;
    }

    public static CustomerDTO fromCustomer(Customer c){
        return new CustomerDTO(c.getCustId(), c.getCompanyName(), c.getContactName(),
                c.getContactTitle(), c.getAddress(), c.getCity(), c.getRegion(), c.getPostalCode(),
                c.getCountry(), c.getPhone(), c.getFax());
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

    public String getContactName() {
        return contactName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public String getFax() {
        return fax;
    }
}
