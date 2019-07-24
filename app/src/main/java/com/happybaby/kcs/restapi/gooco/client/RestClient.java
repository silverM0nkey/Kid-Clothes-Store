package com.happybaby.kcs.restapi.gooco.client;


import com.happybaby.kcs.restapi.gooco.requests.RequestLogin;
import com.happybaby.kcs.restapi.gooco.requests.RequestPostCustomer;
import com.happybaby.kcs.restapi.gooco.requests.ResquestAddItemToCart;
import com.happybaby.kcs.restapi.gooco.requests.ResquestUpdateItemFromCart;
import com.happybaby.kcs.restapi.gooco.responses.ResponseCart;
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
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RestClient {

    String PARAM_FILTERS = "filters";
    String PARAM_WITH_TEXT = "with_text";
    String PARAM_CATEGORY_ID = "category_id";
    String PARAM_ORDER = "order";
    String PARAM_DIR = "dir";
    String PARAM_PAGE = "page";
    String PARAM_LIMIT = "limit";
    String PARAM_NEW_ITEM = "New item";

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
    void postCustomer(@Path("storeId") String storeId, @Body RequestPostCustomer requestPostCustomer);

    @POST("/stores/{storeId}/customer/authorization/token")
    Call<ResponseLogin> login(@Path("storeId") String storeId, @Body RequestLogin requestLogin);

    @GET("/stores/{storeId}/customer")
    Call<ResponseCustomer> getCustomer(@Path("storeId") String storeId, @Header("Authorization") String auth);

    @POST("/stores/{storeId}/cart/item")
    ResponseCart addItemToCart(@Path("storeId") String storeId, @Header("Authorization") String auth, @Body ResquestAddItemToCart resquestAddItemToCart, @QueryMap Map<String, String> params);

    @DELETE("/stores/{storeId}/cart/item/{item}")
    ResponseCart deleteItemFromCart(@Path("storeId") String storeId, @Path("storeId") String item);

    @PUT("/stores/{storeId}/cart/item/{item}")
    ResponseCart updateItemToCart(@Path("storeId") String storeId, @Path("storeId") String item, @Header("Authorization") String auth, @Body ResquestUpdateItemFromCart resquestUpdateItemFromCart);


}
