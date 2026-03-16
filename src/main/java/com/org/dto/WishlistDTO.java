package com.org.dto;

public class WishlistDTO {
    private Integer watchlist_id;
    private Integer product_id;
    private String users_email;

    public WishlistDTO() {
    }

    public WishlistDTO(Integer watchlist_id, Integer product_id, String users_email) {
        this.watchlist_id = watchlist_id;
        this.product_id = product_id;
        this.users_email = users_email;
    }

    public Integer getWatchlist_id() {
        return watchlist_id;
    }

    public void setWatchlist_id(Integer watchlist_id) {
        this.watchlist_id = watchlist_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getUsers_email() {
        return users_email;
    }

    public void setUsers_email(String users_email) {
        this.users_email = users_email;
    }
}
