package com.org.cache;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class cacheBrand {

    @Id
    @Column(name = "brand_id")
    private Integer brandId;

    @Column(name = "brand_name")
    private String brandName;

    @OneToMany(mappedBy = "brand" , fetch = FetchType.LAZY)
    private List<cacheProduct> productList;

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<cacheProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<cacheProduct> productList) {
        this.productList = productList;
    }
}
