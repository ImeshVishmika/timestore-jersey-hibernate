package com.org.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_status")
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id", nullable = false)
    private Integer orderStatusId;

    @Column(name = "status", length = 50)
    private String status;

    public OrderStatus() {
    }

    public OrderStatus(String status) {
        this.status = status;
    }

    public Integer getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(Integer orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Legacy snake_case getters for backward compatibility
    public Integer getOrder_status_id() {
        return orderStatusId;
    }

    public void setOrder_status_id(Integer orderStatusId) {
        this.orderStatusId = orderStatusId;
    }
}
