package org.generation.italy.company.dto;

import org.generation.italy.company.model.Customer;


public class CustomerDTO {
    protected Integer custId;
    protected String companyName;
    protected String contactName;
    protected String contactTitle;
    protected String address;
    protected String city;
    protected String region;
    protected String postalCode;
    protected String country;
    protected String phone;
    protected String fax;

    public CustomerDTO(){}

    public CustomerDTO(Integer custId, String companyName, String contactName, String contactTitle, String address,
                       String city, String region, String postalCode, String country, String phone, String fax) {
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
    public static CustomerDTO fromCustomer(Customer customer){
        return new CustomerDTO(customer.getCustId(), customer.getCompanyName(), customer.getContactTitle(), customer.getContactName(),
                customer.getAddress(),customer.getCity() ,customer.getRegion(),customer.getPostalCode(),
                customer.getCountry(),customer.getPhone(),customer.getFax());
    }
    public static Customer toEntity(CustomerDTO cd){
        return new Customer(cd.getCustId(), cd.getCompanyName(),cd.getContactName(),cd.getContactTitle(),cd.getAddress(),
                cd.getCity(), cd.getRegion(),cd.getPostalCode(),cd.getCountry(),cd.getPhone(),cd.getFax());
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
}
