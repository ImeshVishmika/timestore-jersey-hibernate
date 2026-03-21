package com.org.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_img")
public class ProductImage {

    @Id
    @Column(name = "img_path", nullable = false, length = 150)
    private String imgPath;

    @Column(name = "model_id", nullable = false)
    private Integer modelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", referencedColumnName = "model_id", insertable = false, updatable = false)
    private Model model;

    public String getImg_path() {
        return imgPath;
    }

    public void setImg_path(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getModel_id() {
        return modelId;
    }

    public void setModel_id(Integer modelId) {
        this.modelId = modelId;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
