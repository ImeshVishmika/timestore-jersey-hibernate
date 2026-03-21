package com.org.dto;

public class DeliveryMethodDTO {
    private Integer id;
    private String deliveryMethod;
    private Double price;
    private String deliveryDays;

    public DeliveryMethodDTO() {
    }

    public DeliveryMethodDTO(Integer id, String deliveryMethod, Double price, String deliveryDays) {
        this.id = id;
        this.deliveryMethod = deliveryMethod;
        this.price = price;
        this.deliveryDays = deliveryDays;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDelivery_method() {
        return deliveryMethod;
    }

    public void setDelivery_method(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDelivery_days() {
        return deliveryDays;
    }

    public void setDelivery_days(String deliveryDays) {
        this.deliveryDays = deliveryDays;
    }
}
