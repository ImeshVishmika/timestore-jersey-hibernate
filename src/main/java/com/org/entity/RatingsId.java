package com.org.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RatingsId implements Serializable {

    @Column(name = "user_email", nullable = false, length = 50)
    private String user_email;

    @Column(name = "product_id", nullable = false)
    private Integer product_id;

    public RatingsId() {
    }

    public RatingsId(String user_email, Integer product_id) {
        this.user_email = user_email;
        this.product_id = product_id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof RatingsId that)) {
            return false;
        }
        return Objects.equals(user_email, that.user_email) && Objects.equals(product_id, that.product_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_email, product_id);
    }
}
