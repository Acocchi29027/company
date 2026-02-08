package org.generation.italy.company.dto;

import org.generation.italy.company.model.Customer;

public class CustomerDTO extends CustomerSummaryDTO{
    private String contactName;
    private String contactTitle;
    private String region;
    private String postalCode;
    private String fax;

    public CustomerDTO(Integer custId, String companyName, String contactName, String contactTitle,
                       String address, String city, String region, String postalCode, String country,
                       String phone, String fax) {
        super(custId, companyName, address, city, country, phone);
        this.contactName = contactName;
        this.contactTitle = contactTitle;
        this.region = region;
        this.postalCode = postalCode;
        this.fax = fax;
    }

    public static CustomerDTO fromCustomer (Customer c) {
        return new CustomerDTO(c.getCustId(), c.getCompanyName(), c.getContactName(), c.getContactTitle(),
                c.getAddress(), c.getCity(), c.getRegion(), c.getPostalCode(), c.getCountry(), c.getPhone(),
                c.getFax());
    }
}
