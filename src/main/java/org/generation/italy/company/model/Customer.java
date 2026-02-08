package org.generation.italy.company.model;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custid")
    private Integer custId;
    @Column(name = "companyname")
    private String companyName;
    @Column(name = "contactname")
    private String contactName;
    @Column(name = "contacttitle")
    private String contactTitle;
    private String address;
    private String city;
    private String region;
    @Column(name = "postalcode")
    private String postalCode;
    private String country;
    private String phone;
    private String fax;

    public Customer(){}

    public Customer(Integer custId, String companyName, String contactName, String contactTitle, String address,
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

    public Customer(Integer custId, String companyName, String contactName,
                    String city, String country, String phone) {
        this.custId = custId;
        this.companyName = companyName;
        this.contactName = contactName;
        this.city = city;
        this.country = country;
        this.phone = phone;
    }

    public Integer getCustId() {
        return custId;
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

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", companyName='" + companyName + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactTitle='" + contactTitle + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                ", phone='" + phone + '\'' +
                ", fax='" + fax + '\'' +
                '}';
    }
}
