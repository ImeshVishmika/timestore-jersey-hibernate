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
@Table(name = "watchlist")
public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "watchlist_id", nullable = false)
    private Integer watchlistId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "users_email", nullable = false, length = 50)
    private String usersEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "model_id", insertable = false, updatable = false)
    private Model model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_email", referencedColumnName = "email", insertable = false, updatable = false)
    private User user;
}
