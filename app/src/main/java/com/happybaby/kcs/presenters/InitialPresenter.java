package com.happybaby.kcs.presenters;

import com.happybaby.kcs.activities.interfaces.InitialView;
import com.happybaby.kcs.models.CustomerProfile;
import com.happybaby.kcs.presenters.interfaces.IResponseStores;
import com.happybaby.kcs.restapi.gooco.ConnectionsProfile;
import com.happybaby.kcs.restapi.gooco.responses.ResponseStore;
import com.happybaby.kcs.restapi.gooco.responses.ResponseStoreView;
import com.happybaby.kcs.restapi.gooco.services.Services;

import java.util.List;
import java.util.Locale;


public class InitialPresenter extends BasePresenter implements IResponseStores {

    private InitialView initialView;
    private Locale locale;
    private Services services;

    public InitialPresenter(InitialView initialView, Locale locale) {
        super();
        this.initialView = initialView;
        this.locale = locale;
        this.services = new Services();
    }

    public void loadStore() {
        this.services.loadStore(this);
    }

    public void onResponseStores(List<ResponseStore> stores) {
        if (stores!=null) {
            String countryCode = locale.getCountry();
            String displayLanguage = locale.getDisplayLanguage();
            ResponseStore foundStore = stores.stream().filter(s -> s.getCountryCode().equals(countryCode)).findAny().orElse(null);

            if (foundStore == null) {
                foundStore = stores.stream().filter(s -> s.getCountryCode().equals(ConnectionsProfile.DEFAULT_COUNTRY_CODE)).findAny().orElse(null);
            }
            ResponseStoreView storeView = null;
            if (foundStore != null) {
                storeView = foundStore.getStoreViews().stream().filter(sv -> sv.getName().equals(displayLanguage)).findAny().orElse(null);
                if (storeView == null) {
                    storeView = foundStore.getStoreViews().stream().filter(sv -> sv.getName().equals(ConnectionsProfile.DEFAULT_DISPLAY_LANGUAGE)).findAny().orElse(null);
                }
            }
            int storeId;
            if (storeView != null) {
                storeId = storeView.getStoreId();
            } else {
                storeId = 2;
            }
            CustomerProfile.getCustomerProfile().setStoreId(storeId);
            if (initialView != null) {
                initialView.loadStoreFinished(storeId);
            }
        } else {
            if (initialView != null) {
                initialView.loadStoreFail();
            }
        }
    }
    public void onResponseStoresFail(){
        if (initialView != null) {
            initialView.loadStoreFail();
        }
    }

    public void unbindView(){
        this.initialView = null;
    }
}
