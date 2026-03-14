package com.org.dto;

public class CartDTO {
    private Integer cartId;
    private Integer productId;
    private Integer cartQty;
    private String usersEmail;

    public CartDTO() {}

    public CartDTO(Integer cartId, Integer productId, Integer cartQty, String usersEmail) {
        this.cartId = cartId;
        this.productId = productId;
        this.cartQty = cartQty;
        this.usersEmail = usersEmail;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCartQty() {
        return cartQty;
    }

    public void setCartQty(Integer cartQty) {
        this.cartQty = cartQty;
    }

    public String getUsersEmail() {
        return usersEmail;
    }

    public void setUsersEmail(String usersEmail) {
        this.usersEmail = usersEmail;
    }
}
