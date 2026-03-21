package com.org.entity;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "invoice_items")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_item_id" , nullable = false)
    private  int invoiceItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id",referencedColumnName = "model_id",updatable = false )
    private Model model;

    @Column(name = "model_name", nullable = false, length = 50)
    private String modelName;

    @Column(name = "model_price", nullable = false)
    private Double modelPrice;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @CreationTimestamp
    @Column(name = "date_time" , nullable = false , updatable = false)
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id", insertable = false, updatable = false)
    private Invoice invoice;



    public int getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(int invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Double getModelPrice() {
        return modelPrice;
    }

    public void setModelPrice(Double modelPrice) {
        this.modelPrice = modelPrice;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
