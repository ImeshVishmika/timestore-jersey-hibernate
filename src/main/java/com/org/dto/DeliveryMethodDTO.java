package com.org.dto;

public class DeliveryMethodDTO {
    private Integer id;
    private String delivery_method;
    private Double price;
    private String delivery_days;

    public DeliveryMethodDTO() {
    }

    public DeliveryMethodDTO(Integer id, String delivery_method, Double price, String delivery_days) {
        this.id = id;
        this.delivery_method = delivery_method;
        this.price = price;
        this.delivery_days = delivery_days;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDelivery_method() {
        return delivery_method;
    }

    public void setDelivery_method(String delivery_method) {
        this.delivery_method = delivery_method;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDelivery_days() {
        return delivery_days;
    }

    public void setDelivery_days(String delivery_days) {
        this.delivery_days = delivery_days;
    }
}
