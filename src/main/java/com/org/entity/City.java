package com.org.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", nullable = false)
    private Integer cityId;

    @Column(name = "district_id", nullable = false)
    private Integer districtId;

    @Column(name = "city_en", length = 45)
    private String cityEn;

    @Column(name = "city_si", length = 45)
    private String citySi;

    @Column(name = "city_ta", length = 45)
    private String cityTa;

    @Column(name = "sub_name_en", length = 45)
    private String subNameEn;

    @Column(name = "sub_name_si", length = 45)
    private String subNameSi;

    @Column(name = "sub_name_ta", length = 45)
    private String subNameTa;

    @Column(name = "postcode", length = 15)
    private String postcode;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", referencedColumnName = "district_id", insertable = false, updatable = false)
    private District districts;
}
