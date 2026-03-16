package com.org.dto;

public class OrderDTO {
    private Integer order_id;
    private String email;
    private String ordered_date;
    private Integer delivery_method;
    private Integer order_status;

    public OrderDTO() {
    }

    public OrderDTO(Integer order_id, String email, String ordered_date, Integer delivery_method, Integer order_status) {
        this.order_id = order_id;
        this.email = email;
        this.ordered_date = ordered_date;
        this.delivery_method = delivery_method;
        this.order_status = order_status;
    }

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

    public String getOrdered_date() {
        return ordered_date;
    }

    public void setOrdered_date(String ordered_date) {
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
}
