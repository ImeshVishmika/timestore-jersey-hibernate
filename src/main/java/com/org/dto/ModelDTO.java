package com.org.dto;

import com.org.entity.Model;

public class ModelDTO {
    private Integer modelId;
    private Integer productId;
    private String model;
    private Double price;
    private Integer qty;
    private String addedTime;
    private String imgPath;

    public ModelDTO() {}

    public ModelDTO(Model model) {
        this.modelId = model.getModel_id();
        this.productId = model.getProduct_id();
        this.model = model.getModel();
        this.price = model.getPrice();
        this.qty = model.getQty();
        this.addedTime = model.getAdded_time() != null ? model.getAdded_time().toString() : null;
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
