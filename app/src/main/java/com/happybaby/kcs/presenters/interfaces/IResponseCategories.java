package com.happybaby.kcs.presenters.interfaces;

import com.happybaby.kcs.restapi.gooco.responses.ResponseCategory;

import java.util.List;

public interface IResponseCategories {
    void onResponseCategories(List<ResponseCategory> responseCategories);
    void onResponseCategoriesFail();
}
