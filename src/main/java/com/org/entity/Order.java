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
    private Integer order_id;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "ordered_date")
    private LocalDateTime ordered_date;

    @Column(name = "delivery_method")
    private Integer delivery_method;

    @Column(name = "order_status")
    private Integer order_status;

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
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getOrdered_date() {
        return ordered_date;
    }

    public void setOrdered_date(LocalDateTime ordered_date) {
        this.ordered_date = ordered_date;
    }

    public Integer getDelivery_method() {
        return delivery_method;
    }

    public void setDelivery_method(Integer delivery_method) {
        this.delivery_method = delivery_method;
    }

    public Integer getOrder_status() {
        return order_status;
    }

    public void setOrder_status(Integer order_status) {
        this.order_status = order_status;
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
