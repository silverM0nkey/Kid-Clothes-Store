package com.happybaby.kcs.presenters.interfaces;

import com.happybaby.kcs.restapi.gooco.responses.ResponseProductsResults;

public interface IResponseProducts {
    void onResponseProductsResults(ResponseProductsResults responseProducts);
    void onResponseProductsFail();
}
