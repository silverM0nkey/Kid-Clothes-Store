package com.happybaby.kcs.restapi.gooco.client;


import com.happybaby.kcs.restapi.gooco.requests.RequestLogin;
import com.happybaby.kcs.restapi.gooco.requests.RequestPostCustomer;
import com.happybaby.kcs.restapi.gooco.responses.ResponseCategory;
import com.happybaby.kcs.restapi.gooco.responses.ResponseCustomer;
import com.happybaby.kcs.restapi.gooco.responses.ResponseGeneralInfo;
import com.happybaby.kcs.restapi.gooco.responses.ResponseHome;
import com.happybaby.kcs.restapi.gooco.responses.ResponseLogin;
import com.happybaby.kcs.restapi.gooco.responses.ResponseProductsResults;
import com.happybaby.kcs.restapi.gooco.responses.ResponseStore;


import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RestClient {

    static final String PARAM_FILTERS = "filters";
    static final String PARAM_WITH_TEXT = "with_text";
    static final String PARAM_CATEGORY_ID = "category_id";
    static final String PARAM_ORDER = "order";
    static final String PARAM_DIR = "dir";
    static final String PARAM_PAGE = "page";
    static final String PARAM_LIMIT = "limit";

    @GET("stores/{storeId}/faq")
    Call<ResponseGeneralInfo> getFaq(@Path("storeId") String storeId);

    @GET("stores/{storeId}/privacy")
    Call<ResponseGeneralInfo> getPrivacy(@Path("storeId") String storeId);

    @GET("stores/{storeId}/shopping_guide")
    Call<ResponseGeneralInfo> getShoppingGuide(@Path("storeId") String storeId);

    @GET("stores/{storeId}/shipping_costs")
    Call<ResponseGeneralInfo> getShippingCosts(@Path("storeId") String storeId);

    @GET("stores/{storeId}/contact")
    Call<ResponseGeneralInfo> getContact(@Path("storeId") String storeId);

    @GET("stores")
    Call<List<ResponseStore>> getStores();

    @GET("stores/{storeId}/categories")
    Call<List<ResponseCategory>> getCategories(@Path("storeId") String storeId);

    @GET("stores/{storeId}/products/search")
    Call<ResponseProductsResults> getProducts(@Path("storeId") String storeId, @QueryMap Map<String, String> params);

    @GET("/stores/{storeId}/home")
    Call<ResponseHome> getHome(@Path("storeId") String storeId);

    @POST("/stores/{storeId}/customers")
    void postCustumer(@Path("storeId") String storeId, @Body RequestPostCustomer requestPostCustomer);

    @POST("/stores/{storeId}/customer/authorization/token")
    Call<ResponseLogin> login(@Path("storeId") String storeId, @Body RequestLogin requestLoguin);

    @GET("/stores/{storeId}/customer")
    Call<ResponseCustomer> getCustumer(@Path("storeId") String storeId,   @Header("Authorization") String auth);

}
