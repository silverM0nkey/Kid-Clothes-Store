package com.happybaby.kcs.activities.interfaces;

import com.happybaby.kcs.restapi.gooco.responses.ResponseCategory;

import java.util.List;

public interface MainView extends BaseView {

    void loadCategoriesFinished(List<ResponseCategory> categories);
    void loadCategoriesFail();
}
