package com.org.cache;

import com.org.dto.ProductDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class cacheProduct {

    @Id
    @Column(name = "product_id" , nullable = false)
    private Integer productid;

    @Column(name = "brand_id")
    private Integer brandId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @ManyToOne
    @JoinColumn(name = "brand_id" , referencedColumnName = "brand_id",insertable = false,updatable = false)
    private cacheBrand brand;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
    private List<cacheModel> modelList;

    public cacheProduct(){}

    public cacheProduct(ProductDTO productDTO) {
        this.productName = productDTO.getProductName();
        this.brandId = productDTO.getBrandId();
    }

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

    public cacheBrand getBrand() {
        return brand;
    }

    public void setBrand(cacheBrand brand) {
        this.brand = brand;
    }

    public List<cacheModel> getModelList() {
        return modelList;
    }

    public void setModelList(List<cacheModel> modelList) {
        this.modelList = modelList;
    }
}
