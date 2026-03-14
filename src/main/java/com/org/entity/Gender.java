package com.org.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gender")
public class Gender {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "gender", length = 45)
    private String gender;
}
