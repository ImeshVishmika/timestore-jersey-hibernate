package com.org.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Integer orderId;
    private String email;
    private String firstName;
    private String lastName;
    private String orderedDate;
    private Integer deliveryMethod;
    private Double deliveryFee;
    private Integer orderStatus;
    private String orderStatusName;
    private double total;
    private List<ModelDTO> orderItems;

    public OrderDTO() {
    }

    public OrderDTO(Integer orderId, String email, String firstName, String lastName, Double total, LocalDateTime orderedDate, Integer deliveryMethod, Double deliveryFee, Integer orderStatus, String orderStatusName) {
        this.orderId = orderId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.orderedDate = orderedDate.toString();
        this.total = total;
        this.deliveryMethod = deliveryMethod;
        this.deliveryFee = deliveryFee;
        this.orderStatus = orderStatus;
        this.orderStatusName = orderStatusName;
    }

    public List<ModelDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ModelDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(String orderedDate) {
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

    public String getOrdered_date() {
        return orderedDate;
    }

    public void setOrdered_date(String orderedDate) {
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

    public Double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }
}
