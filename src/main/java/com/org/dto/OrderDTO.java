package com.org.dto;

import java.time.LocalDateTime;

public class OrderDTO {
    private Integer orderId;
    private String email;
    private LocalDateTime orderedDate;
    private Integer deliveryMethod;
    private Integer orderStatus;

    public OrderDTO() {}

    public OrderDTO(Integer orderId, String email, LocalDateTime orderedDate, Integer deliveryMethod, Integer orderStatus) {
        this.orderId = orderId;
        this.email = email;
        this.orderedDate = orderedDate;
        this.deliveryMethod = deliveryMethod;
        this.orderStatus = orderStatus;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
