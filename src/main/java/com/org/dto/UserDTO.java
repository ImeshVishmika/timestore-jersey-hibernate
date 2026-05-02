package com.org.dto;

import com.org.entity.User;

import java.time.LocalDate;

public class UserDTO {
    
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String mobile;
    private Integer genderId;
    private Integer status;
    private String joinedDate;
    private String genderName;
    private String statusName;
    private Integer orderCount;
    private Double totalSpent;
    private String lineOne;
    private String lineTwo;
    private String city;
    private String district;
    private String province;
    private String postalCode;


    public UserDTO() {
    }

    public UserDTO(User user){
        this.firstName= user.getFirstName();
        this.lastName= user.getLastName();
        this.joinedDate=user.getJoinedDate().toString();
        this.email = user.getEmail();
        this.mobile = user.getMobile();
        this.status = user.getStatus();
    }


    public UserDTO(String email,
                   String firstName,
                   String lastName,
                   String mobile,
                   Double totalSpent,
                   Integer orderCount,
                   Integer genderId,
                   Integer status,
                   LocalDate joinedDate) {

        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.genderId = genderId;
        this.totalSpent =totalSpent;
        this.orderCount = orderCount;
        this.status = status;
        this.joinedDate = joinedDate.toString();
    }

    public UserDTO(String email,
                   String firstName,
                   String lastName,
                   String mobile,
                   Long orderCount,
                   Double totalSpent,
                   LocalDate joinedDate) {

        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.orderCount = orderCount == null ? 0 : orderCount.intValue();
        this.totalSpent = totalSpent == null ? 0.0 : totalSpent;
        this.joinedDate = joinedDate == null ? null : joinedDate.toString();
    }

    // Getters andSetters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getJoinedDate() {
        return LocalDate.parse(joinedDate);
    }

    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate.toString();
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public String getLineOne() {
        return lineOne;
    }

    public void setLineOne(String lineOne) {
        this.lineOne = lineOne;
    }

    public String getLineTwo() {
        return lineTwo;
    }

    public void setLineTwo(String lineTwo) {
        this.lineTwo = lineTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
