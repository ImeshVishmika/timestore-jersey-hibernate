package com.org.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

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

    @OneToMany(mappedBy = "order" , fetch = FetchType.LAZY)
    private List<OrderHasModel> orderItems;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(LocalDateTime orderedDate) {
        this.orderedDate = orderedDate;
    }

    public Integer getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(Integer deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderHasModel> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderHasModel> orderItems) {
        this.orderItems = orderItems;
    }

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
