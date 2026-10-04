package com.org.cache;

import jakarta.persistence.*;

@Entity
public class cacheProductImg {

    @Id
    @Column(name = "img_path",nullable = false,length = 150)
    private String imgPath;

    @Column(name = "model_id",nullable = false)
    private Integer modelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id",referencedColumnName = "model_id",insertable = false,updatable = false)
    private cacheModel model;

    public cacheProductImg(cacheModel model){
        this.modelId = model.getModelId();
        this.imgPath = "Image/product/"+model.getModel();
    }

    public cacheProductImg() {

    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public cacheModel getModel() {
        return model;
    }

    public void setModel(cacheModel model) {
        this.model = model;
    }
}
