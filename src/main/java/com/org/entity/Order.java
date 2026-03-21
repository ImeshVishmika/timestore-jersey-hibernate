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
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Integer orderId;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "ordered_date")
    private LocalDateTime orderedDate;

    @Column(name = "delivery_method")
    private Integer deliveryMethod;

    @Column(name = "order_status")
    private Integer orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_method", referencedColumnName = "id", insertable = false, updatable = false)
    private DeliveryMethod deliveryMethodRef;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_status", referencedColumnName = "order_status_id", insertable = false, updatable = false)
    private OrderStatus orderStatusRef;

    public Integer getOrder_id() {
        return orderId;
    }

    public void setOrder_id(Integer orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getOrdered_date() {
        return orderedDate;
    }

    public void setOrdered_date(LocalDateTime orderedDate) {
        this.orderedDate = orderedDate;
    }

    public Integer getDelivery_method() {
        return deliveryMethod;
    }

    public void setDelivery_method(Integer deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public Integer getOrder_status() {
        return orderStatus;
    }

    public void setOrder_status(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DeliveryMethod getDeliveryMethodRef() {
        return deliveryMethodRef;
    }

    public void setDeliveryMethodRef(DeliveryMethod deliveryMethodRef) {
        this.deliveryMethodRef = deliveryMethodRef;
    }

    public OrderStatus getOrderStatusRef() {
        return orderStatusRef;
    }

    public void setOrderStatusRef(OrderStatus orderStatusRef) {
        this.orderStatusRef = orderStatusRef;
    }
}
