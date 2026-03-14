package com.org.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProductHasCategoryId implements Serializable {

    @Column(name = "product_id", nullable = false)
    private Integer product_id;

    @Column(name = "category_id", nullable = false)
    private Integer category_category_id;

    public ProductHasCategoryId() {
    }

    public ProductHasCategoryId(Integer product_id, Integer category_category_id) {
        this.product_id = product_id;
        this.category_category_id = category_category_id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ProductHasCategoryId that)) {
            return false;
        }
        return Objects.equals(product_id, that.product_id) && Objects.equals(category_category_id, that.category_category_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_id, category_category_id);
    }
}
