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
@Table(name = "districts")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id", nullable = false)
    private Integer districtId;

    @Column(name = "province_id", nullable = false)
    private Integer provinceId;

    @Column(name = "district_en", length = 45)
    private String districtEn;

    @Column(name = "district_si", length = 45)
    private String districtSi;

    @Column(name = "district_ta", length = 45)
    private String districtTa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id", referencedColumnName = "province_id", insertable = false, updatable = false)
    private Province province;
}
