package com.happybaby.kcs.activities.interfaces;

import android.content.Context;

public interface GeneralInfoView {

    void loadInfoFinished(String content);
    void loadInfoFail();
    Context getContext();
}
