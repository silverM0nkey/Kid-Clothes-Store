package com.happybaby.kcs.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.happybaby.kcs.activities.LoginActivity;
import com.happybaby.kcs.activities.MainActivity;
import com.happybaby.kcs.R;
import com.happybaby.kcs.fragments.interfaces.ProfileView;
import com.happybaby.kcs.presenters.ProfilePresenter;

public class ProfileFragment extends Fragment implements ProfileView, View.OnClickListener {

    private EditText name;
    private EditText lastName;
    private EditText email;
    private EditText phone;
    private Button saveButton;
    private Button logout;
    private Button login;

    private ProfilePresenter profilePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.profilePresenter = new ProfilePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        name = rootView.findViewById(R.id.name);
        lastName = rootView.findViewById(R.id.last_name);
        email = rootView.findViewById(R.id.email);
        phone = rootView.findViewById(R.id.telephon);
        saveButton = rootView.findViewById(R.id.save_button);
        logout = rootView.findViewById(R.id.logout_button);
        login = rootView.findViewById(R.id.login_button);

        saveButton.setOnClickListener(this);
        logout.setOnClickListener(this);
        login.setOnClickListener(this);

        return rootView;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.save_button) {
            Toast.makeText(getContext(), getResources().getString(R.string.modification_not_available), Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.logout_button) {
            profilePresenter.logout();
            ((MainActivity)getActivity()).shoppingCartInActionbarUpdate();
        } else if  (view.getId() == R.id.login_button) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        profilePresenter.setUp();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        profilePresenter.unbindView();
    }

    public void setUp(){
        profilePresenter.setUp();
    }

    public void setLogin() {
        name.setVisibility(View.GONE);
        lastName.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        phone.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
        logout.setVisibility(View.GONE );
        login.setVisibility(View.VISIBLE);
    }

    public void setCustomerProfile(String sFirstName, String sLastName, String sEmail, String sPhone) {
        name.setVisibility(View.VISIBLE);
        lastName.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        phone.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.VISIBLE);
        logout.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
        name.setText(sFirstName);
        lastName.setText(sLastName);
        email.setText(sEmail);
        phone.setText(sPhone);
    }

    public Context getContext() {
        return getActivity();
    }
}
