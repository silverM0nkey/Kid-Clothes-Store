package com.happybaby.kcs.restapi.gooco;

import android.app.Activity;
import android.content.Intent;


import com.happybaby.kcs.activities.ConnectionErrorActivity;

import retrofit2.Call;
import retrofit2.Callback;

public abstract class CallbackWithRetry<T> implements Callback<T> {

    private static final int TOTAL_RETRIES = 3;
    private int retryCount = 0;
    private Activity activity;

    public CallbackWithRetry(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

        if (retryCount < TOTAL_RETRIES) {
            retryCount++;
            call.clone().enqueue(this);
        } else {
            onFinalFailure();
        }
    }

    public void onFinalFailure() {
        if (activity != null) {
            Intent intent = new Intent(activity, ConnectionErrorActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivity(intent);
        }
    }

}
