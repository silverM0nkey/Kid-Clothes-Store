package com.happybaby.kcs.restapi.gooco.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.happybaby.kcs.activities.GeneralInfoActivity;
import com.happybaby.kcs.models.CustomerProfile;
import com.happybaby.kcs.presenters.interfaces.IResponseCategories;
import com.happybaby.kcs.presenters.interfaces.IResponseCheck;
import com.happybaby.kcs.presenters.interfaces.IResponseGeneralInfo;
import com.happybaby.kcs.presenters.interfaces.IResponseHome;
import com.happybaby.kcs.presenters.interfaces.IResponseLogin;
import com.happybaby.kcs.presenters.interfaces.IResponseProducts;
import com.happybaby.kcs.presenters.interfaces.IResponseStores;
import com.happybaby.kcs.restapi.gooco.CallbackWithRetry;
import com.happybaby.kcs.restapi.gooco.ConnectionsProfile;
import com.happybaby.kcs.restapi.gooco.client.RestClient;
import com.happybaby.kcs.restapi.gooco.requests.RequestLogin;
import com.happybaby.kcs.restapi.gooco.responses.ResponseCategory;
import com.happybaby.kcs.restapi.gooco.responses.ResponseCustomer;
import com.happybaby.kcs.restapi.gooco.responses.ResponseGeneralInfo;
import com.happybaby.kcs.restapi.gooco.responses.ResponseHome;
import com.happybaby.kcs.restapi.gooco.responses.ResponseLogin;
import com.happybaby.kcs.restapi.gooco.responses.ResponseProductsResults;
import com.happybaby.kcs.restapi.gooco.responses.ResponseStore;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Services {

    private static String shippingCostsContent = "<html><header><title>Shipping Costs</title></header><body><h1 ALIGN=\"center\" STYLE=\"font:36pt/40pt courier;\">Hello world!</h1></body></html>";
    protected Retrofit retrofit;
    protected RestClient restClient;

    public Services() {
        setupRestClient();
    }

    protected void setupRestClient(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(ConnectionsProfile.MOCK_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        restClient = retrofit.create(RestClient.class);
    }

    public void loadStore(IResponseStores presenter) {
        Call<List<ResponseStore>> call = restClient.getStores();
        call.enqueue(new CallbackWithRetry<List<ResponseStore>>() {

            @Override
            public void onResponse(Call<List<ResponseStore>> call, Response<List<ResponseStore>> response) {
                if (response.isSuccessful()) {
                    List<ResponseStore> stores = response.body();
                    presenter.onResponseStores(stores);
                } else {
                    presenter.onResponseStores(null);
                }
            }

            public void onFinalFailure() {
                presenter.onResponseStoresFail();
            }

        });
    }


    public void getHomeCategories(String storeId, IResponseHome presenter) {
        Call<ResponseHome> call = restClient.getHome(storeId);
        call.enqueue(new CallbackWithRetry<ResponseHome>() {

            @Override
            public void onResponse(Call<ResponseHome> call, Response<ResponseHome> response) {
                ResponseHome responseHome = null;
                if (response.isSuccessful()) {
                    responseHome = response.body();
                }
                presenter.onResponseHome(responseHome);
            }

            public void onFinalFailure() {
                presenter.onResponseHomeFail();
            }
        });
    }

    public void loadCategories(Integer storeId, IResponseCategories presenter) {
        Call<List<ResponseCategory>> call = restClient.getCategories(Integer.valueOf(storeId).toString());
        call.enqueue(new CallbackWithRetry<List<ResponseCategory>>() {

            @Override
            public void onResponse(Call<List<ResponseCategory>> call, Response<List<ResponseCategory>> response) {
                if (response.isSuccessful()) {
                    List<ResponseCategory> categories = response.body();
                    presenter.onResponseCategories(categories);
                } else {
                    presenter.onResponseCategories(null);
                }
            }

            public void onFinalFailure() {
                presenter.onResponseCategoriesFail();
            }
        });
    }

    public void getProducts(String storeId, String categoryId, IResponseProducts presenter) {
        HashMap<String, String> params = new HashMap<>();
        params.put(restClient.PARAM_CATEGORY_ID, categoryId);

        Call<ResponseProductsResults> call = restClient.getProducts(storeId, params);
        call.enqueue(new CallbackWithRetry<ResponseProductsResults>() {

            @Override
            public void onResponse(Call<ResponseProductsResults> call, Response<ResponseProductsResults> response) {
                ResponseProductsResults responseProductsResults = null;
                if (response.isSuccessful()) {
                    responseProductsResults = response.body();
                }
                presenter.onResponseProductsResults(responseProductsResults);
            }

            public void onFinalFailure() {
                presenter.onResponseProductsFail();
            }
        });
    }

    public void checkConnection(IResponseCheck presenter) {
        Call<ResponseGeneralInfo> call = restClient.getFaq("1");
        call.enqueue(new Callback<ResponseGeneralInfo>() {
            @Override
            public void onResponse(Call<ResponseGeneralInfo> call, Response<ResponseGeneralInfo> response) {
                if(response.isSuccessful()) {
                    presenter.onResponseOk();
                } else {
                    presenter.onResponseFail();
                }
            }

            @Override
            public void onFailure(Call<ResponseGeneralInfo> call, Throwable t) {
                presenter.onResponseFail();
            }
        });
    }

    public void loadContent(String storeId, int selectedItem, IResponseGeneralInfo presenter){
        Call<ResponseGeneralInfo> call = null;
        if (selectedItem == GeneralInfoActivity.Types.TYPE_FAQ.ordinal()){
            call = restClient.getFaq(storeId);        }
        else  if  (selectedItem == GeneralInfoActivity.Types.TYPE_SHOPPING_GUIDE.ordinal()) {
            call = restClient.getShoppingGuide(storeId);
        } else  if  (selectedItem == GeneralInfoActivity.Types.TYPE_SHIPPING_COSTS.ordinal()) {
            //Endpoint returns malformed json
            //call = restClient.getShippingCosts(storeId);
        }  else  if  (selectedItem == GeneralInfoActivity.Types.TYPE_CONTACT.ordinal()) {
            call = restClient.getContact(storeId);
        }

        if (call != null) {
            call.enqueue(new CallbackWithRetry<ResponseGeneralInfo>() {

                @Override
                public void onResponse(Call<ResponseGeneralInfo> call, Response<ResponseGeneralInfo> response) {
                    if (response.isSuccessful()) {
                        ResponseGeneralInfo responseGeneralInfo = response.body();
                        presenter.loadInfoFinished(responseGeneralInfo);
                    } else {
                        presenter.loadInfoFinished(null);
                    }
                }


                public void onFinalFailure() {
                    presenter.loadInfoFail();
                }
            });
        } else {
            ResponseGeneralInfo responseGeneralInfo = new ResponseGeneralInfo();
            responseGeneralInfo.setContent(shippingCostsContent);
            presenter.loadInfoFinished(responseGeneralInfo);
        }
    }

    public void login(String email, String password, IResponseLogin presenter) {
        RequestLogin requestLogin = new RequestLogin(ConnectionsProfile.DEFAULT_GRANT_TYPE, email, password, ConnectionsProfile.CLIENT_ID);
        Call<ResponseLogin> call = restClient.login(CustomerProfile.getCustomerProfile().getStoreId().toString(), requestLogin);
        call.enqueue(new CallbackWithRetry<ResponseLogin>() {

            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    ResponseLogin responseLogin = response.body();

                    Call<ResponseCustomer> callGetCustomer = restClient.getCustomer(CustomerProfile.getCustomerProfile().getStoreId().toString(), responseLogin.getAccess_token());
                    callGetCustomer.enqueue(new CallbackWithRetry<ResponseCustomer>() {

                        @Override
                        public void onResponse(Call<ResponseCustomer> call, Response<ResponseCustomer> response) {
                            if (response.isSuccessful()) {
                                ResponseCustomer responseCustomer = response.body();
                                presenter.loginFinished(responseCustomer);
                            } else {
                                presenter.loginFinished(null);
                            }
                        }

                        public void onFinalFailure() {
                            presenter.loginFail();
                        }
                    });

                } else {
                    presenter.loginFinished(null);
                }
            }

            public void onFinalFailure() {
                presenter.loginFail();
            }
        });
    }

}
