package com.happybaby.kcs.presenters;

import com.happybaby.kcs.activities.interfaces.LoginView;
import com.happybaby.kcs.models.CustomerProfile;
import com.happybaby.kcs.restapi.gooco.CallbackWithRetry;
import com.happybaby.kcs.restapi.gooco.ConnectionsProfile;
import com.happybaby.kcs.restapi.gooco.requests.RequestLogin;
import com.happybaby.kcs.restapi.gooco.responses.ResponseCustomer;
import com.happybaby.kcs.restapi.gooco.responses.ResponseLogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class LoginPresenter extends BasePresenter {

    LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        super(loginView.getContext());
        this.loginView = loginView;
    }

    public void login(String email, String password) {
        RequestLogin requestLogin = new RequestLogin(ConnectionsProfile.DEFAULT_GRANT_TYPE, email, password, ConnectionsProfile.CLIENT_ID);
        Call<ResponseLogin> call = restClient.login(CustomerProfile.getCustomerProfile().getStoreId().toString(), requestLogin);
        call.enqueue(new CallbackWithRetry<ResponseLogin>(loginView.getContext()) {

            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    ResponseLogin responseLogin = response.body();

                    Call<ResponseCustomer> callGetCustomer = restClient.getCustomer(CustomerProfile.getCustomerProfile().getStoreId().toString(), responseLogin.getAccess_token());
                    callGetCustomer.enqueue(new CallbackWithRetry<ResponseCustomer>(loginView.getContext()) {

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

                                shoppingCartInteractor.changeShoppingCartInLogin(responseCustomer.getEmail());

                                loginView.loginFinished();
                            } else {
                                loginView.loginFail();
                            }
                        }
                    });

                } else {
                    loginView.loginFail();
                }
            }
        });
    }

    public void unbindView(){
        this.loginView = null;
    }

}
