package com.org.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_address")
public class UserAddress {

    @Column(name = "address_line1", length = 100)
    private String address_line1;

    @Column(name = "address_line2", length = 100)
    private String address_line2;

    @Column(name = "address_city_id")
    private Integer address_city_id;

    @Id
    @Column(name = "users_email", nullable = false, length = 50)
    private String users_email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_city_id", referencedColumnName = "city_id", insertable = false, updatable = false)
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_email", referencedColumnName = "email", insertable = false, updatable = false)
    private User user;
}
