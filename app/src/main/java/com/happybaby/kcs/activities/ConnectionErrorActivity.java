package com.happybaby.kcs.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.happybaby.kcs.R;
import com.happybaby.kcs.activities.interfaces.ConnectionErrorView;
import com.happybaby.kcs.presenters.ConnectionErrorPresenter;
import com.happybaby.kcs.restapi.gooco.ConnectionsProfile;
import com.happybaby.kcs.restapi.gooco.client.RestClient;

public class ConnectionErrorActivity extends AppCompatActivity implements ConnectionErrorView, View.OnClickListener {

    private RestClient restClient;
    ProgressBar progressBar;
    ConnectionErrorPresenter connectionErrorPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_error);

        this.connectionErrorPresenter = new ConnectionErrorPresenter(this);

        Button tryAgainButton = findViewById(R.id.try_again_button);
        tryAgainButton.setOnClickListener(this);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        connectionErrorPresenter.checkConnection();
    }

    public void finishActivity() {
        finish();
    }

    public void hideProgressBar(){
        try {
            wait(ConnectionsProfile.WAIT_TIME_IN_TRY_AGAIN_TO_CONNECT);
            progressBar.setVisibility(View.GONE);
        } catch (Exception e){
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        //Do nothing
    }

    @Override
    public Context getContext() {
        return null;
    }
}