package com.org.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_has_model")
public class ProductHasModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id", nullable = false)
    private Integer modelId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "model", length = 45)
    private String model;

    @Column(name = "price")
    private Double price;

    @Column(name = "qty")
    private Integer qty;

    @Column(name = "added_time")
    private LocalDateTime addedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    private Product product;

    public Integer getModel_id() {
        return modelId;
    }

    public void setModel_id(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getProduct_id() {
        return productId;
    }

    public void setProduct_id(Integer productId) {
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

    public LocalDateTime getAdded_time() {
        return addedTime;
    }

    public void setAdded_time(LocalDateTime addedTime) {
        this.addedTime = addedTime;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
