package com.happybaby.kcs.activities.interfaces;

import android.content.Context;

import com.happybaby.kcs.restapi.gooco.responses.ResponseCategory;

import java.util.List;

public interface MainView {

    void loadCategoriesFinished(List<ResponseCategory> categories);
    void loadCategoriesFail();
    Context getContext();
}
