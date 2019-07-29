package com.happybaby.kcs.presenters;

import com.happybaby.kcs.activities.interfaces.MainView;
import com.happybaby.kcs.restapi.gooco.CallbackWithRetry;
import com.happybaby.kcs.restapi.gooco.responses.ResponseCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainPresenter extends BasePresenter {

    MainView mainView;

    public MainPresenter(MainView mainView) {
        super(mainView.getContext());
        this.mainView = mainView;
    }

    public void loadCategories(Integer storeId) {
        Call<List<ResponseCategory>> call = restClient.getCategories(Integer.valueOf(storeId).toString());
        call.enqueue(new CallbackWithRetry<List<ResponseCategory>>(mainView.getContext()) {

            @Override
            public void onResponse(Call<List<ResponseCategory>> call, Response<List<ResponseCategory>> response) {
                if (response.isSuccessful()) {
                    List<ResponseCategory> categories = response.body();
                    if (mainView != null) {
                        mainView.loadCategoriesFinished(categories);
                    }
                } else {
                    System.out.print(response.errorBody());
                    if (mainView != null) {
                        mainView.loadCategoriesFail();
                    }
                }
            }
        });
    }

    public int countAllProductsByCurrentUser() {
        return shoppingCartInteractor.countAllCurrentUserProducts();
    }

    public void unbindView(){
        this.mainView = null;
    }

}
