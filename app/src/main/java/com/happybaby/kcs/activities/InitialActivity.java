package com.happybaby.kcs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.happybaby.kcs.R;
import com.happybaby.kcs.activities.interfaces.InitialView;
import com.happybaby.kcs.presenters.InitialPresenter;


public class InitialActivity extends BaseActivity implements InitialView {

    private InitialPresenter initialPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        initialPresenter = new InitialPresenter(this, getResources().getConfiguration().getLocales().get(0));
    }

    @Override
    public void onStart() {
        super.onStart();
        initialPresenter.loadStore();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        initialPresenter.unbindView();
    }

    public void loadStoreFinished(Integer storeId) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(GeneralInfoActivity.PARAM_STORE_ID, storeId);
        startActivity(intent);
        finish();
    }

    public void loadStoreFail() {
        Toast.makeText(this, getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
    }
}
