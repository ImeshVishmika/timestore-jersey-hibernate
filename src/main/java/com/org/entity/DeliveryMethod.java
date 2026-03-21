package com.org.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "delivery_method")
public class DeliveryMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "delivery_method", length = 50)
    private String deliveryMethod;

    @Column(name = "price")
    private Double price;

    @Column(name = "delivery_days", length = 50)
    private String deliveryDays;

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
