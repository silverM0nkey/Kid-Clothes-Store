package com.happybaby.kcs.restapi.gooco;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;


import com.happybaby.kcs.activities.ConnectionErrorActivity;

import retrofit2.Call;
import retrofit2.Callback;

public abstract class CallbackWithRetry<T> implements Callback<T> {

    private static final int TOTAL_RETRIES = 3;
    private int retryCount = 0;
    private Context context;

    public CallbackWithRetry(Context context) {
        this.context = context;
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
        if (context != null) {
            Intent intent = new Intent(context, ConnectionErrorActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
        }
    }

}
