package com.org.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "product_name", length = 50)
    private String productName;

    @Column(name = "brand_id")
    private Integer brandId;

    @Column(name = "description")
    private String des;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", referencedColumnName = "brand_id", insertable = false, updatable = false)
    private Brand brand;

    @OneToMany(mappedBy = "product" , fetch = FetchType.LAZY)
    private List<Model> modelList;


    public List<Model> getModel() {
        return modelList;
    }

    public void setModel(List<Model> modelList) {
        this.modelList = modelList;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
