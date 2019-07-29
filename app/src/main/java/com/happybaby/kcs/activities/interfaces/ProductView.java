package com.happybaby.kcs.activities.interfaces;

import com.happybaby.kcs.models.SizeModel;
import com.happybaby.kcs.restapi.gooco.responses.ResponseSize;

import java.util.List;

public interface ProductView extends BaseView {

    void loadSizeModelFinished(List<SizeModel> items);
    void selectionFinished(ResponseSize currentSize);
    void showSizePopup(boolean addFrom);
    void updateCartIcon(boolean showMessage);
    void setOriginalPrice(String finalPrice);
    void setFinalPrice(String finalPrice);
    void goToShoppingCart();
    void showEmptyCartMessage();
}
