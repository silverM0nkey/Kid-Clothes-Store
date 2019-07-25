package com.happybaby.kcs.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.happybaby.kcs.R;
import com.happybaby.kcs.models.CustomerProfile;
import com.happybaby.kcs.restapi.gooco.CallbackWithRetry;
import com.happybaby.kcs.restapi.gooco.ConnectionsProfile;
import com.happybaby.kcs.restapi.gooco.responses.ResponseStore;
import com.happybaby.kcs.restapi.gooco.responses.ResponseStoreView;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

public class InitialActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
    }

    @Override
    public void onStart() {
        super.onStart();
        Context context = this;
        Call<List<ResponseStore>> call = restClient.getStores();
        call.enqueue(new CallbackWithRetry<List<ResponseStore>>(this) {

            @Override
            public void onResponse(Call<List<ResponseStore>> call, Response<List<ResponseStore>> response) {
                if (response.isSuccessful()) {
                    Locale locale = getResources().getConfiguration().getLocales().get(0);

                    String countryCode = locale.getCountry();
                    String displayLanguage = locale.getDisplayLanguage();
                    List<ResponseStore> stores = response.body();
                    ResponseStore foundStore = stores.stream().filter(s -> s.getCountryCode().equals(countryCode)).findAny().orElse(null);

                    if (foundStore == null) {
                        foundStore = stores.stream().filter(s -> s.getCountryCode().equals(ConnectionsProfile.DEFAULT_COUNTRY_CODE)).findAny().orElse(null);
                    }
                    ResponseStoreView storeView = null;
                    if (foundStore != null){
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
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra(GeneralInfoActivity.PARAM_STORE_ID, storeId);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(context, getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
