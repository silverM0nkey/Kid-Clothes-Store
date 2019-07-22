package com.happybaby.kcs.restapi.gooco.responses;

import java.util.List;

public class ResponseHomeCategory {

    private String categoryId;
    private String label;
    private String imageUrl;
    private List<ResponseHomeCategory> children;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<ResponseHomeCategory> getChildren() {
        return children;
    }

    public void setChildren(List<ResponseHomeCategory> children) {
        this.children = children;
    }
}
