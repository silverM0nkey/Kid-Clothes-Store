package com.happybaby.kcs.restapi.gooco.responses;

import java.util.List;

public class ResponseHome {

    private List<ResponseHomeCategory> categories;

    public List<ResponseHomeCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ResponseHomeCategory> categories) {
        this.categories = categories;
    }
}
