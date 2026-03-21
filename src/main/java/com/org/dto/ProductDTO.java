package com.org.dto;

import com.org.entity.Product;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductDTO {

    private Integer productId;
    private String productName;
    private Integer brandId;
    private String brandName;
    private Double price;
    private Long soldCount;
    private List<ModelDTO> models;
    private Long stock;
    private Double revenue;
    private String date;

    private List<Double> revenueList ;
    private List<String> dateList ;


    // Constructors
    public ProductDTO() {
       revenueList = new ArrayList<>();
       dateList = new ArrayList<>();
    }

    public ProductDTO(double revenue, Date date) {
        this.date = date.toString();
        this.revenue = revenue;
    }

    public ProductDTO(double revenue, Integer value) {
        this.revenue = revenue;
        this.date = String.valueOf(value);
    }

    public ProductDTO(Product p) {
        this.productName = p.getProductName();
        this.productId = p.getProductId();
        this.brandName = p.getBrand().getBrandName();
        this.brandId = p.getBrandId();
    }

    public ProductDTO(Integer productId,
                      String productName,
                      Integer brandId,
                      String brandName,
                      double price,
                      long stock,
                      long soldCount,
                      double revenue) {

        this.productId = productId;
        this.productName = productName;
        this.brandId = brandId;
        this.brandName = brandName;
        this.price = price;
        this.soldCount = soldCount;
        this.revenue = revenue;
        this.stock = stock;
    }

    // Getters andSetters


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void addToDateList(Date date){
        dateList.add(date.toString());
    }

    public void addToDateList(String date){
        dateList.add(date);
    }

    public void addToRevenueList(Double revenue){
        revenueList.add(revenue);
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public List<Double> getRevenueList() {
        return revenueList;
    }

    public void setRevenueList(List<Double> revenueList) {
        this.revenueList = revenueList;
    }

    public List<String> getDateList() {
        return dateList;
    }

    public void setDateList(List<String> dateList) {
        this.dateList = dateList;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(Long soldCount) {
        this.soldCount = soldCount;
    }


    public List<ModelDTO> getModels() {
        return models;
    }

    public void setModels(List<ModelDTO> models) {
        this.models = models;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
