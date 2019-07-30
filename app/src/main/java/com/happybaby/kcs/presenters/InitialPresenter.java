package com.happybaby.kcs.presenters;

import com.happybaby.kcs.activities.interfaces.InitialView;
import com.happybaby.kcs.models.CustomerProfile;
import com.happybaby.kcs.restapi.gooco.CallbackWithRetry;
import com.happybaby.kcs.restapi.gooco.ConnectionsProfile;
import com.happybaby.kcs.restapi.gooco.responses.ResponseStore;
import com.happybaby.kcs.restapi.gooco.responses.ResponseStoreView;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;


public class InitialPresenter extends BasePresenter {

    InitialView initialView;

    public InitialPresenter(InitialView initialView) {
        super();
        this.initialView = initialView;
    }

    public void loadStore() {
        Call<List<ResponseStore>> call = restClient.getStores();
        call.enqueue(new CallbackWithRetry<List<ResponseStore>>(initialView.getContext()) {

            @Override
            public void onResponse(Call<List<ResponseStore>> call, Response<List<ResponseStore>> response) {
                if (response.isSuccessful()) {
                    Locale locale = initialView.getContext().getResources().getConfiguration().getLocales().get(0);

                    String countryCode = locale.getCountry();
                    String displayLanguage = locale.getDisplayLanguage();
                    List<ResponseStore> stores = response.body();
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
        });
    }

    public void unbindView(){
        this.initialView = null;
    }
}
