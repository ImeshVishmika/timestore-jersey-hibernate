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
    private Integer city_id;

    @Column(name = "district_id", nullable = false)
    private Integer district_id;

    @Column(name = "city_en", length = 45)
    private String city_en;

    @Column(name = "city_si", length = 45)
    private String city_si;

    @Column(name = "city_ta", length = 45)
    private String city_ta;

    @Column(name = "sub_name_en", length = 45)
    private String sub_name_en;

    @Column(name = "sub_name_si", length = 45)
    private String sub_name_si;

    @Column(name = "sub_name_ta", length = 45)
    private String sub_name_ta;

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
