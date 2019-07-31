package com.happybaby.kcs.presenters;

import com.happybaby.kcs.activities.interfaces.MainView;
import com.happybaby.kcs.presenters.interfaces.IResponseCategories;
import com.happybaby.kcs.restapi.gooco.responses.ResponseCategory;
import com.happybaby.kcs.restapi.gooco.services.Services;

import java.util.List;

public class MainPresenter extends BasePresenter implements IResponseCategories {

    MainView mainView;
    Services services;

    public MainPresenter(MainView mainView) {
        super();
        this.mainView = mainView;
        this.services = new Services();
    }

    public void loadCategories(Integer storeId) {
        this.services.loadCategories(storeId, this);
    }

    public void onResponseCategories(List<ResponseCategory> categories){
        if (categories != null) {
            if (mainView != null) {
                mainView.loadCategoriesFinished(categories);
            }
        } else {
            if (mainView != null) {
                mainView.loadCategoriesFail();
            }
        }
    }

    public void onResponseCategoriesFail(){
        if (mainView != null) {
            mainView.showConnectionErrorActivity();
        }
    }

    public int countAllProductsByCurrentUser() {
        return shoppingCartInteractor.countAllCurrentUserProducts();
    }

    public void unbindView(){
        this.mainView = null;
    }

}
