package com.happybaby.kcs.presenters;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.happybaby.kcs.bd.room.entities.ShoppingCartProduct;
import com.happybaby.kcs.restapi.gooco.ConnectionsProfile;
import com.happybaby.kcs.restapi.gooco.client.RestClient;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BasePresenter {

    protected Retrofit retrofit;
    protected RestClient restClient;

    public BasePresenter() {
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
}
