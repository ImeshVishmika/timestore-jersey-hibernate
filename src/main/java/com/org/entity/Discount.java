package com.org.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "discount")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_id", nullable = false)
    private Integer discountId;

    @Column(name = "discount_code", nullable = false, unique = true, length = 50)
    private String discountCode;

    @Column(name = "description", length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;  // PRODUCT or ORDER

    @Enumerated(EnumType.STRING)
    @Column(name = "value_type", nullable = false)
    private ValueType valueType;  // PERCENTAGE or FIXED_AMOUNT

    @Column(name = "discount_value", nullable = false)
    private Double discountValue;  // e.g., 10 (for 10% or Rs. 10)

    @Column(name = "usage_limit", nullable = false)
    private Integer usageLimit;  // Maximum times this discount can be used

    @Column(name = "times_used", nullable = false)
    private Integer timesUsed = 0;  // Current usage count

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @Column(name = "minimum_order_value")
    private Double minimumOrderValue;  // Nullable - no minimum if null

    @Column(name = "minimum_product_quantity")
    private Integer minimumProductQuantity;  // For product discounts - nullable

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DiscountStatus status = DiscountStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    // Constructors
    public Discount() {}

    public Discount(String discountCode, String description, DiscountType discountType, 
                    ValueType valueType, Double discountValue, Integer usageLimit, 
                    LocalDateTime expiryDate, Double minimumOrderValue) {
        this.discountCode = discountCode;
        this.description = description;
        this.discountType = discountType;
        this.valueType = valueType;
        this.discountValue = discountValue;
        this.usageLimit = usageLimit;
        this.expiryDate = expiryDate;
        this.minimumOrderValue = minimumOrderValue;
        this.timesUsed = 0;
        this.status = DiscountStatus.ACTIVE;
    }

    // Getters and Setters
    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public Integer getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(Integer usageLimit) {
        this.usageLimit = usageLimit;
    }

    public Integer getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(Integer timesUsed) {
        this.timesUsed = timesUsed;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Double getMinimumOrderValue() {
        return minimumOrderValue;
    }

    public void setMinimumOrderValue(Double minimumOrderValue) {
        this.minimumOrderValue = minimumOrderValue;
    }

    public Integer getMinimumProductQuantity() {
        return minimumProductQuantity;
    }

    public void setMinimumProductQuantity(Integer minimumProductQuantity) {
        this.minimumProductQuantity = minimumProductQuantity;
    }

    public DiscountStatus getStatus() {
        return status;
    }

    public void setStatus(DiscountStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    // Utility Methods
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    public boolean isUsageLimitReached() {
        return timesUsed >= usageLimit;
    }

    public boolean isActive() {
        return status == DiscountStatus.ACTIVE && !isExpired() && !isUsageLimitReached();
    }

    public void incrementUsageCount() {
        this.timesUsed++;
        this.updatedDate = LocalDateTime.now();
    }

    // Enums
    public enum DiscountType {
        PRODUCT("Product Discount"),
        ORDER("Order Discount");

        private final String displayName;

        DiscountType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum ValueType {
        PERCENTAGE("Percentage (%)"),
        FIXED_AMOUNT("Fixed Amount (Rs.)");

        private final String displayName;

        ValueType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum DiscountStatus {
        ACTIVE("Active"),
        INACTIVE("Inactive"),
        ARCHIVED("Archived");

        private final String displayName;

        DiscountStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
