package org.generation.italy.company.dto;

import org.generation.italy.company.model.Customer;

public class CustomerDTO {

    protected Integer custId;
    protected String companyName;
    protected String contactName;
    protected String city;
    protected String country;
    protected String phone;
    protected String contactTitle;
    protected String address;
    protected String region;
    protected String postalCode;
    protected String fax;


    public CustomerDTO(Integer custId,
                       String companyName,
                       String contactName,
                       String city,
                       String country,
                       String phone,
                       String contactTitle,
                       String address,
                       String region,
                       String postalCode,
                       String fax) {
        this.custId = custId;
        this.companyName = companyName;
        this.contactName = contactName;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.contactTitle = contactTitle;
        this.address = address;
        this.region = region;
        this.postalCode = postalCode;
        this.fax = fax;
    }

    public static CustomerDTO summaryFromCustomer(Customer customer) {
        return new CustomerDTO(
                customer.getCustId(),
                customer.getCompanyName(),
                customer.getContactName(),
                customer.getCity(),
                customer.getCountry(),
                customer.getPhone(),
                customer.getContactTitle(),
                customer.getAddress(),
                customer.getRegion(),
                customer.getPostalCode(),
                customer.getFax()
        );
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
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

    public static CustomerDTO fromCustomer(Customer c) {
        return new CustomerDTO(
                c.getCustId(),
                c.getCompanyName(),
                c.getContactName(),
                c.getCity(),
                c.getCountry(),
                c.getPhone(),
                c.getContactTitle(),
                c.getAddress(),
                c.getRegion(),
                c.getPostalCode(),
                c.getFax()
        );
    }

    public Customer toEntity() {
        return new Customer(
                custId,
                companyName,
                contactName,
                contactTitle,   // contactTitle
                address,   // address
                city,
                region,   // region
                postalCode,   // postalCode
                country,
                phone,
                fax    // fax
        );
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
}
