package com.happybaby.kcs.presenters;

import com.happybaby.kcs.activities.interfaces.ConnectionErrorView;
import com.happybaby.kcs.restapi.gooco.responses.ResponseGeneralInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ConnectionErrorPresenter extends BasePresenter {

    ConnectionErrorView connectionErrorView;

    public ConnectionErrorPresenter(ConnectionErrorView connectionErrorView) {
        super();
        this.connectionErrorView = connectionErrorView;
    }

    public void checkConnection() {
        Call<ResponseGeneralInfo> call = restClient.getFaq("1");
        call.enqueue(new Callback<ResponseGeneralInfo>() {
            @Override
            public void onResponse(Call<ResponseGeneralInfo> call, Response<ResponseGeneralInfo> response) {
                if(response.isSuccessful()) {
                   if (connectionErrorView != null) {
                       connectionErrorView.finishActivity();
                   }
                } else {
                    connectionErrorView.hideProgressBar();
                }
            }

            @Override
            public void onFailure(Call<ResponseGeneralInfo> call, Throwable t) {
                connectionErrorView.hideProgressBar();
            }
        });
    }

    public void unbindView(){
        this.connectionErrorView = null;
    }
}
