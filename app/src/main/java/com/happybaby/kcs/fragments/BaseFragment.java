package com.happybaby.kcs.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.happybaby.kcs.restapi.gooco.ConnectionsProfile;
import com.happybaby.kcs.restapi.gooco.client.RestClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseFragment extends Fragment {

    protected Retrofit retrofit;
    protected RestClient restClient;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
