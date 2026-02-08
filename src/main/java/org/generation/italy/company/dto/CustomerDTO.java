package org.generation.italy.company.dto;

import org.generation.italy.company.model.Customer;

public class CustomerDTO extends CustomerSummaryDTO {

    private String contactTitle;
    private String address;
    private String region;
    private String postalCode;
    private String fax;

    public CustomerDTO() {}

    public CustomerDTO(Integer custId, String companyName, String contactName, String contactTitle,
                       String address, String city, String region, String postalCode,
                       String country, String phone, String fax) {
        super(custId, companyName, contactName, city, country, phone);
        this.contactTitle = contactTitle;
        this.address = address;
        this.region = region;
        this.postalCode = postalCode;
        this.fax = fax;
    }

    public static CustomerDTO fromCustomer(Customer c) {
        return new CustomerDTO(
                c.getCustId(),
                c.getCompanyName(),
                c.getContactName(),
                c.getContactTitle(),
                c.getAddress(),
                c.getCity(),
                c.getRegion(),
                c.getPostalCode(),
                c.getCountry(),
                c.getPhone(),
                c.getFax()
        );
    }

    @Override
    public Customer toEntity() {
        return new Customer(
                custId,
                companyName,
                contactName,
                contactTitle,
                address,
                city,
                region,
                postalCode,
                country,
                phone,
                fax
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
