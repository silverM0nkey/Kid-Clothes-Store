package com.happybaby.kcs.presenters.interfaces;

import com.happybaby.kcs.restapi.gooco.responses.ResponseHome;

public interface IResponseHome {
    void onResponseHome(ResponseHome responseHome);
    void onResponseHomeFail();
}
