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
import com.happybaby.kcs.models.CustomerProfile;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private EditText name;
    private EditText lastName;
    private EditText email;
    private EditText phone;
    private Button saveButton;
    private Button logout;
    private Button login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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
        Context context = this.getContext();
        if (view.getId() == R.id.save_button) {
            Toast.makeText(context, getResources().getString(R.string.modification_not_available), Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.logout_button) {
            CustomerProfile.getCustomerProfile().logout();
            ((MainActivity)getActivity()).shoppingCartInActionbarUpdate();
            setUp();
        } else if  (view.getId() == R.id.login_button) {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setUp();
    }

    public void setUp() {
        if (CustomerProfile.getCustomerProfile().getEmail().equals(CustomerProfile.CUSTOMER_ANONIMOUS)) {
            name.setVisibility(View.GONE);
            lastName.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
            phone.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
            logout.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
        } else {
            name.setVisibility(View.VISIBLE);
            lastName.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);
            phone.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
            login.setVisibility(View.GONE);
            name.setText(CustomerProfile.getCustomerProfile().getFirstName());
            lastName.setText(CustomerProfile.getCustomerProfile().getLastName());
            email.setText(CustomerProfile.getCustomerProfile().getEmail());
            phone.setText(CustomerProfile.getCustomerProfile().getPhone());

        }
    }
}
