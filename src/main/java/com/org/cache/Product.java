package com.org.cache;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Product {

    @Id
    @Column(name = "product_id" , nullable = false)
    private Integer productid;

    @Column(name = "brand_id")
    private Integer brandId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @ManyToOne
    @JoinColumn(name = "brand_id" , referencedColumnName = "brand_id",insertable = false,updatable = false)
    private Brand brand;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
    private List<Model> modelList;

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Model> getModelList() {
        return modelList;
    }

    public void setModelList(List<Model> modelList) {
        this.modelList = modelList;
    }
}
