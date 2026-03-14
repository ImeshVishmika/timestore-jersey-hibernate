package com.org.entity;

import java.io.Serializable;
import java.util.Objects;

public class BuyNowCartId implements Serializable {

    private String user_email;
    private Integer model_id;

    public BuyNowCartId() {
    }

    public BuyNowCartId(String user_email, Integer model_id) {
        this.user_email = user_email;
        this.model_id = model_id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BuyNowCartId that)) {
            return false;
        }
        return Objects.equals(user_email, that.user_email) && Objects.equals(model_id, that.model_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_email, model_id);
    }
}
