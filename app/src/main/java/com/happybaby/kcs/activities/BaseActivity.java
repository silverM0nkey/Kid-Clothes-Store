package com.happybaby.kcs.activities;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.happybaby.kcs.R;
import com.happybaby.kcs.drawables.BadgeDrawable;
import com.happybaby.kcs.restapi.gooco.ConnectionsProfile;
import com.happybaby.kcs.restapi.gooco.client.RestClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseActivity extends AppCompatActivity  {

    protected Toolbar toolbar;
    private Retrofit retrofit;
    protected RestClient restClient;

    protected void onCreate(Bundle savedInstanceState) {
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

    protected void setupToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    protected static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }
}