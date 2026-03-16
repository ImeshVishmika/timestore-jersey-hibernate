package com.org.dto;

public class InvoiceDTO {
    private Integer invoiceId;
    private Integer orderId;
    private String invoiceDate;
    private String email;
    private Double deliveryFee;

    public InvoiceDTO() {
    }

    public InvoiceDTO(Integer invoiceId, Integer orderId, String invoiceDate, String email, Double deliveryFee) {
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

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
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
