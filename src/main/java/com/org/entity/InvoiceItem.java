package com.org.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoice_items")
public class InvoiceItem {

    @EmbeddedId
    private InvoiceItemsId id;


    @Column(name = "product_name", nullable = false, length = 50)
    private String product_name;

    @Column(name = "product_price", nullable = false)
    private Double product_price;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id", insertable = false, updatable = false)
    private Invoice invoice;
}
