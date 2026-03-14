package com.org.dto;

import java.time.LocalDateTime;

public class InvoiceDTO {
    private Integer invoiceId;
    private Integer orderId;
    private LocalDateTime invoiceDate;
    private String email;
    private Double deliveryFee;

    public InvoiceDTO() {}

    public InvoiceDTO(Integer invoiceId, Integer orderId, LocalDateTime invoiceDate, String email, Double deliveryFee) {
        this.invoiceId = invoiceId;
        this.orderId = orderId;
        this.invoiceDate = invoiceDate;
        this.email = email;
        this.deliveryFee = deliveryFee;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
}
