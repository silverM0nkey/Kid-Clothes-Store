package com.happybaby.kcs.presenters.interfaces;

import com.happybaby.kcs.restapi.gooco.responses.ResponseStore;

import java.util.List;

public interface IResponseStores {
    void onResponseStores(List<ResponseStore> responseStores);
    void onResponseStoresFail();
}
