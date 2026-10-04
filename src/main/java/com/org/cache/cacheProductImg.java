package com.org.cache;

import com.org.entity.ProductImage;
import jakarta.persistence.*;

@Entity
@Table(name = "product_img")
public class ProductImg {

    @Id
    @Column(name = "img_path",nullable = false,length = 150)
    private String imgPath;

    @Column(name = "model_id",nullable = false)
    private Integer modelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id",referencedColumnName = "model_id",insertable = false,updatable = false)
    private Model model;

    public ProductImg(Model model){
        this.modelId = model.getModelId();
        this.imgPath = "Image/product/"+model.getModel();
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

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
