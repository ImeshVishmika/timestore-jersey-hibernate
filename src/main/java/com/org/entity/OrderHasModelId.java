package com.org.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrderHasModelId implements Serializable {

    @Column(name = "order_id", nullable = false)
    private Integer orderId;

    @Column(name = "model_id", nullable = false)
    private Integer modelId;

    public OrderHasModelId() {
    }

    public OrderHasModelId(Integer orderId, Integer modelId) {
        this.orderId = orderId;
        this.modelId = modelId;
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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof OrderHasModelId that)) {
            return false;
        }
        return Objects.equals(orderId, that.orderId) && Objects.equals(modelId, that.modelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, modelId);
    }
}
