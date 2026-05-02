package com.org.dto;

import java.util.List;

public class FilterDTO {

    private String email;
    private String searchQuery;
    private Integer userStatusId ;

    private List<Integer> brandId;
    private List<String> brandName;

    private List<Integer> productId;
    private String productName;

    private List<Integer> modelId;
    private String modelName;

    private String sort;
    private int periodDays;
    private String startDate;
    private String endDate;
    private String dateFrom;
    private String dateTo;
    private String viewBy;
    private double minRevenue;
    private double maxRevenue;
    private Double minPrice;
    private Double maxPrice;

    private Integer orderStausId;
    private Integer minOrderCount;
    private Integer maxOrderCount;
    private Double minSpent;
    private Double maxSpent;
    private String joinedDateFrom;
    private String joinedDateTo;

    public String getViewBy() {
        viewBy=(viewBy==null)?"":viewBy;
        return switch (viewBy.toUpperCase()) {
            case "WEEK" -> "WEEK";
            case "MONTH" -> "MONTH";
            case "YEAR" -> "YEAR";
            default -> "DATE";
        };
    }

    public Integer getUserStatusId() {
        return userStatusId;
    }

    public void setUserStatusId(Integer userStatusId) {
        this.userStatusId = userStatusId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOrderStausId() {
        return orderStausId;
    }

    public void setOrderStausId(Integer orderStausId) {
        this.orderStausId = orderStausId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getPeriodDays() {
        return periodDays;
    }

    public void setPeriodDays(int periodDays) {
        this.periodDays = periodDays;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setViewBy(String viewBy) {
        this.viewBy = viewBy;
    }

    public double getMinRevenue() {
        return minRevenue;
    }

    public void setMinRevenue(double minRevenue) {
        this.minRevenue = minRevenue;
    }

    public double getMaxRevenue() {
        return maxRevenue;
    }

    public void setMaxRevenue(double maxRevenue) {
        this.maxRevenue = maxRevenue;
    }

    public List<Integer> getBrandId() {
        return brandId;
    }

    public void setBrandId(List<Integer> brandId) {
        this.brandId = brandId;
    }

    public List<String> getBrandName() {
        return brandName;
    }

    public void setBrandName(List<String> brandName) {
        this.brandName = brandName;
    }

    public List<Integer> getProductId() {
        return productId;
    }

    public void setProductId(List<Integer> productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<Integer> getModelId() {
        return modelId;
    }

    public void setModelId(List<Integer> modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getJoinedDateFrom() {
        return joinedDateFrom != null ? joinedDateFrom : dateFrom;
    }

    public void setJoinedDateFrom(String joinedDateFrom) {
        this.joinedDateFrom = joinedDateFrom;
    }

    public String getJoinedDateTo() {
        return joinedDateTo != null ? joinedDateTo : dateTo;
    }

    public void setJoinedDateTo(String joinedDateTo) {
        this.joinedDateTo = joinedDateTo;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public Integer getMinOrderCount() {
        return minOrderCount;
    }

    public void setMinOrderCount(Integer minOrderCount) {
        this.minOrderCount = minOrderCount;
    }

    public Integer getMaxOrderCount() {
        return maxOrderCount;
    }

    public void setMaxOrderCount(Integer maxOrderCount) {
        this.maxOrderCount = maxOrderCount;
    }

    public Double getMinSpent() {
        return minSpent;
    }

    public void setMinSpent(Double minSpent) {
        this.minSpent = minSpent;
    }

    public Double getMaxSpent() {
        return maxSpent;
    }

    public void setMaxSpent(Double maxSpent) {
        this.maxSpent = maxSpent;
    }
}
