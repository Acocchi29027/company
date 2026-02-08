package org.generation.italy.company.dto;

import org.generation.italy.company.model.Customer;

public class CustomerSummaryDTO {

    protected Integer custId;
    protected String companyName;
    protected String contactName;
    protected String city;
    protected String country;
    protected String phone;

    public CustomerSummaryDTO(){}

    public CustomerSummaryDTO(Integer custId, String companyName, String contactName,
                              String city, String country, String phone) {
        this.custId = custId;
        this.companyName = companyName;
        this.contactName = contactName;
        this.city = city;
        this.country = country;
        this.phone = phone;
    }

    public static CustomerSummaryDTO summaryFromCustomer(Customer customer){
        return new CustomerSummaryDTO(
                customer.getCustId(),
                customer.getCompanyName(),
                customer.getContactName(),
                customer.getCity(),
                customer.getCountry(),
                customer.getPhone()
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

    public Customer toEntity(){
        return new Customer(
                custId,
                companyName,
                contactName,
                null,   // contactTitle
                null,   // address
                city,
                null,   // region
                null,   // postalCode
                country,
                phone,
                null    // fax
        );
    }
}
