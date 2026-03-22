package com.org.dto;

import com.org.entity.OrderHasModel;

public class OrderItemsDTO {
    private Integer orderId;
    private Integer modelId;
    private Integer qty;
    private String modelName;
    private String productName;
    private Double price;

    public OrderItemsDTO() {
    }

    public OrderItemsDTO(Integer orderId, Integer modelId, Integer qty, String modelName, String productName, Double price) {
        this.orderId = orderId;
        this.modelId = modelId;
        this.qty = qty;
        this.modelName = modelName;
        this.productName = productName;
        this.price = price;
    }

    public OrderItemsDTO(OrderHasModel orderHasModel) {
        if (orderHasModel != null) {
            this.orderId = orderHasModel.getId().getOrderId();
            this.modelId = orderHasModel.getId().getModelId();
            this.qty = orderHasModel.getQty();
            this.price = orderHasModel.getModelPrice();
            
            if (orderHasModel.getModel() != null) {
                this.modelName = orderHasModel.getModel().getModel();
                if (this.price == null) {
                    this.price = orderHasModel.getModel().getPrice();
                }
                
                if (orderHasModel.getModel().getProduct() != null) {
                    this.productName = orderHasModel.getModel().getProduct().getProductName();
                }
            }
        }
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
