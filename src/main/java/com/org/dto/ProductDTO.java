package com.org.dto;

import com.org.entity.Model;

import java.util.List;

public class ProductDTO {
    
    private Integer productId;
    private String productName;
    private Integer brandId;
    private String brandName;
    private double price;
    private List<ModelDTO> models;


    // Constructors
    public ProductDTO() {
    }

    public ProductDTO(String productName) {
        this.productName = productName;
    }

    public ProductDTO(Integer productId, String productName, Integer brandId) {
        this.productId = productId;
        this.productName = productName;
        this.brandId = brandId;
    }

    public ProductDTO(Integer productId, String productName, Integer brandId, String brandName) {
        this.productId = productId;
        this.productName = productName;
        this.brandId = brandId;
        this.brandName = brandName;
    }

    // Getters and Setters


    public List<ModelDTO> getModels() {
        return models;
    }

    public void setModels(List<ModelDTO> models) {
        this.models = models;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
