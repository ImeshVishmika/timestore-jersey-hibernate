package com.org.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "model")
public class Model {

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

    @OneToMany( mappedBy = "model" , fetch = FetchType.LAZY)
    private List<InvoiceItem> invoiceItemList;


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

    public LocalDateTime getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(LocalDateTime addedTime) {
        this.addedTime = addedTime;
    }

    public List<InvoiceItem> getInvoiceItemList() {
        return invoiceItemList;
    }

    public void setInvoiceItemList(List<InvoiceItem> invoiceItemList) {
        this.invoiceItemList = invoiceItemList;
    }

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
