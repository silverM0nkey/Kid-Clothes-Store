package com.happybaby.kcs.presenters;

import com.happybaby.kcs.activities.interfaces.GeneralInfoView;
import com.happybaby.kcs.presenters.interfaces.IResponseGeneralInfo;
import com.happybaby.kcs.restapi.gooco.responses.ResponseGeneralInfo;
import com.happybaby.kcs.restapi.gooco.services.Services;


public class GeneralInfoPresenter extends BasePresenter implements IResponseGeneralInfo {

    private GeneralInfoView generalInfoView;
    private Services services;

    public GeneralInfoPresenter(GeneralInfoView generalInfoView) {
        super();
        this.generalInfoView = generalInfoView;
        this.services = new Services();
    }

    public void loadContent(String storeId, int selectedItem) {
        services.loadContent(storeId, selectedItem, this);
    }

    public void loadInfoFinished(ResponseGeneralInfo responseGeneralInfo){
        if (generalInfoView != null) {
            if (responseGeneralInfo != null) {
                generalInfoView.loadInfoFinished(responseGeneralInfo.getContent());
            } else {
                generalInfoView.loadInfoFinished(null);
            }
        }
    }

    public void loadInfoFail() {
        if (generalInfoView != null) {
            generalInfoView.showConnectionErrorActivity();
        }
    }

    public void unbindView(){
        this.generalInfoView = null;
    }

}
