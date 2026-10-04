package com.org.cache;

import com.org.dto.ModelDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Model {

    @Id
    @Column(name = "model_id")
    private Integer modelId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "model",length = 45)
    private String model;

    @Column(name = "price")
    private Double price;

    @Column(name = "qty")
    private  Integer qty;

    @Column(name = "added_time")
    private LocalDateTime addedTime;

    @Column(name = "color")
    private String color;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",referencedColumnName = "product_id",insertable = false,updatable = false)
    private Product product;

    public Model(){}


    public Model(ModelDTO modelDTO) {
        this.model = modelDTO.getModel();
        this.price = modelDTO.getPrice();
        this.qty = modelDTO.getQty();
        this.addedTime = LocalDateTime.now();

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

    public LocalDateTime getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(LocalDateTime addedTime) {
        this.addedTime = addedTime;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
