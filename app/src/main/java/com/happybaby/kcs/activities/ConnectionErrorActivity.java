package com.happybaby.kcs.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.happybaby.kcs.R;
import com.happybaby.kcs.restapi.gooco.ConnectionsProfile;
import com.happybaby.kcs.restapi.gooco.client.RestClient;
import com.happybaby.kcs.restapi.gooco.responses.ResponseGeneralInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionErrorActivity extends AppCompatActivity implements View.OnClickListener {

    private RestClient restClient;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_error);

        Button tryAgainButton = findViewById(R.id.try_again_button);
        tryAgainButton.setOnClickListener(this);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConnectionsProfile.MOCK_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        restClient = retrofit.create(RestClient.class);
    }

    @Override
    public void onClick(View v) {

        progressBar.setVisibility(View.VISIBLE);
        Call<ResponseGeneralInfo> call = restClient.getFaq("1");
        call.enqueue(new Callback<ResponseGeneralInfo>() {
            @Override
            public void onResponse(Call<ResponseGeneralInfo> call, Response<ResponseGeneralInfo> response) {
                if(response.isSuccessful()) {
                    finish();
                } else {
                    try {
                        ConnectionErrorActivity.this.wait(ConnectionsProfile.WAIT_TIME_IN_TRY_AGAIN_TO_CONNECT);
                        progressBar.setVisibility(View.GONE);
                    } catch (Exception e){
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseGeneralInfo> call, Throwable t) {
                try {
                    ConnectionErrorActivity.this.wait(ConnectionsProfile.WAIT_TIME_IN_TRY_AGAIN_TO_CONNECT);
                    progressBar.setVisibility(View.GONE);
                } catch (Exception e){
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //Do nothing
    }
}