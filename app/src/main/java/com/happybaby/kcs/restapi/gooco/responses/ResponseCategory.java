package com.happybaby.kcs.restapi.gooco.responses;

import java.util.List;

public class ResponseCategory {

    private String categoryId;
    private String name;
    private List<ResponseCategory> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ResponseCategory> getChildren() {
        return children;
    }

    public void setChildren(List<ResponseCategory> children) {
        this.children = children;
    }

    public String getCategoryId() {

        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
