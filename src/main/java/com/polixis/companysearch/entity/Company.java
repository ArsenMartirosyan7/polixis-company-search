package com.polixis.companysearch.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String companyNumber;

    @Column(nullable = false)
    private String name;

    private String status;

    private String companyType;

    private LocalDate incorporatedOn;

    private LocalDate dissolvedOn;

    @Column(length = 1000)
    private String registeredAddress;

    public Company() {
    }

    public Long getId() {
        return id;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public LocalDate getIncorporatedOn() {
        return incorporatedOn;
    }

    public void setIncorporatedOn(LocalDate incorporatedOn) {
        this.incorporatedOn = incorporatedOn;
    }

    public LocalDate getDissolvedOn() {
        return dissolvedOn;
    }

    public void setDissolvedOn(LocalDate dissolvedOn) {
        this.dissolvedOn = dissolvedOn;
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }
}
