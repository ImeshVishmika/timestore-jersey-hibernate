package com.org.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_has_model")
public class OrderHasModel {

    @EmbeddedId
    private OrderHasModelId id;

    @Column(name = "qty")
    private Integer qty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", referencedColumnName = "model_id", insertable = false, updatable = false)
    private Model model;

    public OrderHasModel() {
    }

    public OrderHasModel(OrderHasModelId id, Integer qty) {
        this.id = id;
        this.qty = qty;
    }

    public OrderHasModelId getId() {
        return id;
    }

    public void setId(OrderHasModelId id) {
        this.id = id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
