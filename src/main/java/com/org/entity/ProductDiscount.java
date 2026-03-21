package com.org.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_discount")
public class ProductDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_discount_id", nullable = false)
    private Integer productDiscountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Product product;

    @Column(name = "product_id", insertable = false, updatable = false, nullable = false)
    private Integer productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id", referencedColumnName = "discount_id", nullable = false)
    private Discount discount;

    @Column(name = "discount_id", insertable = false, updatable = false, nullable = false)
    private Integer discountId;

    // Constructors
    public ProductDiscount() {}

    public ProductDiscount(Integer productId, Integer discountId) {
        this.productId = productId;
        this.discountId = discountId;
    }

    public ProductDiscount(Product product, Discount discount) {
        this.product = product;
        this.productId = product.getProductId();
        this.discount = discount;
        this.discountId = discount.getDiscountId();
    }

    // Getters and Setters
    public Integer getProductDiscountId() {
        return productDiscountId;
    }

    public void setProductDiscountId(Integer productDiscountId) {
        this.productDiscountId = productDiscountId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        if (product != null) {
            this.productId = product.getProductId();
        }
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
        if (discount != null) {
            this.discountId = discount.getDiscountId();
        }
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }
}
