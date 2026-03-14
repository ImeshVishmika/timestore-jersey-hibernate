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
    private Integer district_id;

    @Column(name = "province_id", nullable = false)
    private Integer province_id;

    @Column(name = "district_en", length = 45)
    private String district_en;

    @Column(name = "district_si", length = 45)
    private String district_si;

    @Column(name = "district_ta", length = 45)
    private String district_ta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id", referencedColumnName = "province_id", insertable = false, updatable = false)
    private Province province;
}
