package com.happybaby.kcs.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.happybaby.kcs.R;
import com.happybaby.kcs.bd.room.AppDatabase;
import com.happybaby.kcs.bd.room.entities.ShoppingCartProduct;
import com.happybaby.kcs.models.CustomerProfile;
import com.happybaby.kcs.restapi.gooco.CallbackWithRetry;
import com.happybaby.kcs.restapi.gooco.ConnectionsProfile;
import com.happybaby.kcs.restapi.gooco.requests.RequestLogin;
import com.happybaby.kcs.restapi.gooco.responses.ResponseCustomer;
import com.happybaby.kcs.restapi.gooco.responses.ResponseLogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class LoginActivity extends BaseActivity {



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupToolbar();
        setupRestClient();
        setTitle(getResources().getString(R.string.login_title));

        TextView loginJoinText = findViewById(R.id.login_join_text);
        loginJoinText.setText(String.format("%s %s %s",
                getResources().getString(R.string.login_join_text_1),
                getResources().getString(R.string.app_name),
                getResources().getString(R.string.login_join_text_2)));

        Button join = findViewById(R.id.join_button);
        Button login = findViewById(R.id.login_button);

        EditText email =findViewById(R.id.email);
        EditText password = findViewById(R.id.password);

        Context context = this;

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, context.getResources().getString(R.string.join_message), Toast.LENGTH_SHORT).show();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               login(email.getText().toString(), password.getText().toString());
            }
        });
    }

    private void login(String email, String password) {
        Activity activity = this;

        RequestLogin requestLogin = new RequestLogin(ConnectionsProfile.DEFAULT_GRANT_TYPE, email, password, ConnectionsProfile.CLIENT_ID);
        Call<ResponseLogin> call = restClient.login(CustomerProfile.getCustomerProfile().getStoreId().toString(), requestLogin);
        call.enqueue(new CallbackWithRetry<ResponseLogin>(this) {

            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    ResponseLogin responseLogin = response.body();

                    Call<ResponseCustomer> callGetCustomer = restClient.getCustumer(CustomerProfile.getCustomerProfile().getStoreId().toString(), responseLogin.getAccess_token());
                    callGetCustomer.enqueue(new CallbackWithRetry<ResponseCustomer>(activity) {

                        @Override
                        public void onResponse(Call<ResponseCustomer> call, Response<ResponseCustomer> response) {
                            if (response.isSuccessful()) {
                                ResponseCustomer responseCustomer = response.body();

                                CustomerProfile.getCustomerProfile().setData(
                                        responseCustomer.getIdentification(),
                                        responseCustomer.getEmail(),
                                        responseCustomer.getFirstName(),
                                        responseCustomer.getLastName(),
                                        responseCustomer.getPhone(),
                                        responseCustomer.getIsGoccoAndFriends());

                                List<ShoppingCartProduct> procuctList = AppDatabase.getInstance(activity).shoppingCartDao().findProductsByCustomer(CustomerProfile.CUSTOMER_ANONIMOUS);

                                for (ShoppingCartProduct product: procuctList){
                                    product.setCustomer(responseCustomer.getEmail());
                                }
                                AppDatabase.getInstance(activity).shoppingCartDao().insertAll(procuctList);
                                AppDatabase.getInstance(activity).shoppingCartDao().deleteProductsByCustomer(CustomerProfile.CUSTOMER_ANONIMOUS);
                                finish();
                            } else {
                                Toast.makeText(activity, activity.getResources().getString(R.string.login_faild), Toast.LENGTH_SHORT);
                            }
                        }
                    });

                } else {
                    Toast.makeText(activity, activity.getResources().getString(R.string.login_faild), Toast.LENGTH_SHORT);
                }
            }
        });
    }
}


