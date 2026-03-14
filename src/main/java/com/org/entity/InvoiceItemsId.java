package com.org.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class InvoiceItemsId implements Serializable {

    @Column(name = "invoice_id", nullable = false)
    private Integer invoice_id;

    @Column(name = "product_id", nullable = false)
    private Integer product_id;

    public InvoiceItemsId() {
    }

    public InvoiceItemsId(Integer invoice_id, Integer product_id) {
        this.invoice_id = invoice_id;
        this.product_id = product_id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof InvoiceItemsId that)) {
            return false;
        }
        return Objects.equals(invoice_id, that.invoice_id) && Objects.equals(product_id, that.product_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoice_id, product_id);
    }
}
