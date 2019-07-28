package com.happybaby.kcs.activities.interfaces;

import android.content.Context;

public interface InitialView {

    void loadStoreFinished(Integer storeId);
    void loadStoreFail();
    Context getContext();
}
