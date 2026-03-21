package com.org.dto;

import com.org.entity.Discount;
import java.time.LocalDateTime;

public class DiscountDTO {

    private Integer discountId;
    private String discountCode;
    private String description;
    private String discountType;  // "PRODUCT" or "ORDER"
    private String valueType;     // "PERCENTAGE" or "FIXED_AMOUNT"
    private Double discountValue;
    private Integer usageLimit;
    private Integer timesUsed;
    private LocalDateTime expiryDate;
    private Double minimumOrderValue;
    private Integer minimumProductQuantity;
    private String status;  // "ACTIVE", "INACTIVE", "ARCHIVED"
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // Constructors
    public DiscountDTO() {}

    public DiscountDTO(Discount discount) {
        this.discountId = discount.getDiscountId();
        this.discountCode = discount.getDiscountCode();
        this.description = discount.getDescription();
        this.discountType = discount.getDiscountType().name();
        this.valueType = discount.getValueType().name();
        this.discountValue = discount.getDiscountValue();
        this.usageLimit = discount.getUsageLimit();
        this.timesUsed = discount.getTimesUsed();
        this.expiryDate = discount.getExpiryDate();
        this.minimumOrderValue = discount.getMinimumOrderValue();
        this.minimumProductQuantity = discount.getMinimumProductQuantity();
        this.status = discount.getStatus().name();
        this.createdDate = discount.getCreatedDate();
        this.updatedDate = discount.getUpdatedDate();
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

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    // Helper methods
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    public boolean isUsageLimitReached() {
        return timesUsed >= usageLimit;
    }

    public boolean isActive() {
        return "ACTIVE".equals(status) && !isExpired() && !isUsageLimitReached();
    }

    public Integer getRemainingUsage() {
        return usageLimit - timesUsed;
    }
}
