package com.org.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "provinces")
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "province_id", nullable = false)
    private Integer province_id;

    @Column(name = "province_en", nullable = false, length = 45)
    private String province_en;

    @Column(name = "province_si", length = 45)
    private String province_si;

    @Column(name = "province_ta", length = 45)
    private String province_ta;
}
