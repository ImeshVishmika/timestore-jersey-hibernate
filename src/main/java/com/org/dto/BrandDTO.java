package com.org.dto;

import com.org.cache.cacheBrand;
import com.org.entity.Brand;

public class BrandDTO {
    private Integer brandId;
    private String brandName;

    public BrandDTO() {}

    public BrandDTO(Brand brand) {
        this.brandId = brand.getBrandId();
        this.brandName = brand.getBrandName();
    }

    public BrandDTO(cacheBrand cacheBrand) {
        this.brandId = cacheBrand.getBrandId();
        this.brandName = cacheBrand.getBrandName();
    }

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
}
