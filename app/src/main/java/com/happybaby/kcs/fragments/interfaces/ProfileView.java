package com.happybaby.kcs.fragments.interfaces;

import android.content.Context;


public interface ProfileView {

    void setUp();
    void setLogin();
    void setCustomerProfile(String sFirstName, String sLastName, String sEmail, String sPhone);
    Context getContext();
}
