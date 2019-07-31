package com.happybaby.kcs.presenters;

import com.happybaby.kcs.activities.interfaces.LoginView;
import com.happybaby.kcs.models.CustomerProfile;
import com.happybaby.kcs.presenters.interfaces.IResponseLogin;
import com.happybaby.kcs.restapi.gooco.responses.ResponseCustomer;
import com.happybaby.kcs.restapi.gooco.services.Services;


public class LoginPresenter extends BasePresenter implements IResponseLogin {

    LoginView loginView;
    Services services;

    public LoginPresenter(LoginView loginView) {
        super();
        this.loginView = loginView;
        this.services = new Services();
    }

    public void login(String email, String password) {
        this.services.login(email, password, this);
    }


    public void loginFinished(ResponseCustomer responseCustomer){
        if (responseCustomer!=null) {
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

    public void loginFail(){
        loginView.showConnectionErrorActivity();
    }

    public void unbindView(){
        this.loginView = null;
    }
}
