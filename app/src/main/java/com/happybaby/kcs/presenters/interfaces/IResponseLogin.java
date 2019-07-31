package com.happybaby.kcs.presenters.interfaces;

import com.happybaby.kcs.restapi.gooco.responses.ResponseCustomer;

public interface IResponseLogin {
    void loginFinished(ResponseCustomer responseCustomer);
    void loginFail();
}
