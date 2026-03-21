package com.org.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProductHasCategoryId implements Serializable {

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "category_id", nullable = false)
    private Integer categoryCategoryId;

    public ProductHasCategoryId() {
    }

    public ProductHasCategoryId(Integer productId, Integer categoryCategoryId) {
        this.productId = productId;
        this.categoryCategoryId = categoryCategoryId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ProductHasCategoryId that)) {
            return false;
        }
        return Objects.equals(productId, that.productId) && Objects.equals(categoryCategoryId, that.categoryCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, categoryCategoryId);
    }
}
