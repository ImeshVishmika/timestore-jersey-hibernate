package com.org.dto;

import com.org.entity.Model;

public class ModelDTO {
    private Integer modelId;
    private String model;
    private Integer productId;
    private String productName;
    private Integer brandId;
    private String brandName;
    private Double price;
    private Integer qty;
    private String addedTime;
    private String imgPath;
    private Long soldCount;
    private double  revenue;

    public ModelDTO() {}

    public ModelDTO(Integer modelId,String model,Integer brandId,String brandName,Double price,Integer stock,Long soldCount,Double revenue){
        this.modelId=modelId;
        this.model=model;
        this.price=price;
        this.soldCount=soldCount;
        this.revenue= revenue;
        this.brandId=brandId;
        this.brandName=brandName;
        this.qty=stock;
    }

    public ModelDTO(Model model) {
        this.modelId = model.getModel_id();
        this.productId = model.getProduct_id();
        this.productName = model.getProduct().getProductName() ;
        this.brandId = model.getProduct().getBrandId();
        this.brandName = model.getProduct().getBrand().getBrandName();
        this.model = model.getModel();
        this.price = model.getPrice();
        this.qty = model.getQty();
        this.addedTime = model.getAdded_time() != null ? model.getAdded_time().toString() : null;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public Long getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(Long soldCount) {
        this.soldCount = soldCount;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }
}
