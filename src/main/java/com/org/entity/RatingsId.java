package com.org.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RatingsId implements Serializable {

    @Column(name = "user_email", nullable = false, length = 50)
    private String userEmail;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    public RatingsId() {
    }

    public RatingsId(String userEmail, Integer productId) {
        this.userEmail = userEmail;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof RatingsId that)) {
            return false;
        }
        return Objects.equals(userEmail, that.userEmail) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, productId);
    }
}
