package com.org.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrderHasModelId implements Serializable {

    @Column(name = "order_id", nullable = false)
    private Integer order_id;

    @Column(name = "model_id", nullable = false)
    private Integer model_id;

    public OrderHasModelId() {
    }

    public OrderHasModelId(Integer order_id, Integer model_id) {
        this.order_id = order_id;
        this.model_id = model_id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof OrderHasModelId that)) {
            return false;
        }
        return Objects.equals(order_id, that.order_id) && Objects.equals(model_id, that.model_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_id, model_id);
    }
}
