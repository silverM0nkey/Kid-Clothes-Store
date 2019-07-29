package com.happybaby.kcs.activities.interfaces;

import android.content.Context;

import com.happybaby.kcs.models.SizeModel;
import com.happybaby.kcs.restapi.gooco.responses.ResponseSize;

import java.util.List;

public interface ProductView {

    void loadSizeModelFinished(List<SizeModel> items);
    void selectionFinished(ResponseSize currentSize);
    void showSizePopup(boolean addFrom);
    void updateCartIcon();
    void setOriginalPrice(String finalPrice);
    void setFinalPrice(String finalPrice);
    void goToShoppingCart();
    void showEmptyCartMessage();
    Context getContext();
}
