package com.org.dto;

import com.org.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

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


    public UserDTO(String email, String firstName, String lastName, String password, 
                   String mobile, Integer genderId, Integer status, LocalDate joinedDate) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.mobile = mobile;
        this.genderId = genderId;
        this.status = status;
        this.joinedDate = joinedDate.toString();
    }

    // Getters and Setters
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
}
