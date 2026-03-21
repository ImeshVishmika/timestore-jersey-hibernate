package com.org.dto;

public class WishlistDTO {
    private Integer watchlistId;
    private Integer productId;
    private String usersEmail;

    public WishlistDTO() {
    }

    public WishlistDTO(Integer watchlistId, Integer productId, String usersEmail) {
        this.watchlistId = watchlistId;
        this.productId = productId;
        this.usersEmail = usersEmail;
    }

    public Integer getWatchlist_id() {
        return watchlistId;
    }

    public void setWatchlist_id(Integer watchlistId) {
        this.watchlistId = watchlistId;
    }

    public Integer getProduct_id() {
        return productId;
    }

    public void setProduct_id(Integer productId) {
        this.productId = productId;
    }

    public String getUsers_email() {
        return usersEmail;
    }

    public void setUsers_email(String usersEmail) {
        this.usersEmail = usersEmail;
    }
}
