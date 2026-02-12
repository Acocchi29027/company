package org.generation.italy.company.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empid")
    private Integer empId;
    private String lastname;
    private String firstname;
    private String title;
    @Column(name = "titleofcourtesy")
    private String titleOfCourtesy;
    private LocalDateTime birthdate;
    private LocalDateTime hiredate;
    private String address;
    private String city;
    private String region;
    private String postalcode;
    private String country;
    private String phone;
    @ManyToOne
    @JoinColumn(name = "mgrid")
    private Employee manager;

    public Employee(){}

    public Employee(Integer empId, String lastname, String firstname, String title, String titleOfCourtesy,
                    LocalDateTime birthdate, LocalDateTime hiredate, String address, String city, String region,
                    String postalcode, String country, String phone, Employee manager) {
        this.empId = empId;
        this.lastname = lastname;
        this.firstname = firstname;
        this.title = title;
        this.titleOfCourtesy = titleOfCourtesy;
        this.birthdate = birthdate;
        this.hiredate = hiredate;
        this.address = address;
        this.city = city;
        this.region = region;
        this.postalcode = postalcode;
        this.country = country;
        this.phone = phone;
        this.manager = manager;
    }

    public Integer getEmpId() {
        return empId;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleOfCourtesy() {
        return titleOfCourtesy;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public LocalDateTime getHiredate() {
        return hiredate;
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

    public String getPostalcode() {
        return postalcode;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public Employee getManager() {
        return manager;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleOfCourtesy(String titleOfCourtesy) {
        this.titleOfCourtesy = titleOfCourtesy;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public void setHiredate(LocalDateTime hiredate) {
        this.hiredate = hiredate;
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

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }
}
