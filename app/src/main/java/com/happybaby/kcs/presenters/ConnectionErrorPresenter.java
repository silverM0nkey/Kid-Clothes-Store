package com.happybaby.kcs.presenters;

import com.happybaby.kcs.activities.interfaces.ConnectionErrorView;
import com.happybaby.kcs.presenters.interfaces.IResponseCheck;
import com.happybaby.kcs.restapi.gooco.services.Services;

public class ConnectionErrorPresenter extends BasePresenter implements IResponseCheck {

    ConnectionErrorView connectionErrorView;
    private Services services;

    public ConnectionErrorPresenter(ConnectionErrorView connectionErrorView) {
        super();
        this.services = new Services();
        this.connectionErrorView = connectionErrorView;
    }

    public void checkConnection() {
        this.services.checkConnection(this);
    }

    public void onResponseOk() {
        if (connectionErrorView != null) {
            connectionErrorView.finishActivity();
        }
    }

    public void onResponseFail(){
        if (connectionErrorView != null) {
            connectionErrorView.hideProgressBar();
        }
    }

    public void unbindView(){
        this.connectionErrorView = null;
    }
}
