package org.generation.italy.company.dto;

import org.generation.italy.company.model.Supplier;

public class SupplierMinimalDTO {
    private Integer supplierId;
    private String companyName;

    public SupplierMinimalDTO() {
    }

    public SupplierMinimalDTO(Integer supplierId, String companyName) {
        this.supplierId = supplierId;
        this.companyName = companyName;
    }

    public static SupplierMinimalDTO minimalFromSupplier(Supplier supplier){
        return new SupplierMinimalDTO(supplier.getSupplierId(), supplier.getCompanyName());
    }


    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
