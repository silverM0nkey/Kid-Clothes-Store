package com.happybaby.kcs.presenters.interfaces;

import com.happybaby.kcs.restapi.gooco.responses.ResponseGeneralInfo;

public interface IResponseGeneralInfo {
    void loadInfoFinished(ResponseGeneralInfo responseGeneralInfo);
    void loadInfoFail();
}
