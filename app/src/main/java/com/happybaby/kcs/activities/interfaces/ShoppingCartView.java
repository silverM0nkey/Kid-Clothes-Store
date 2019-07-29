package com.happybaby.kcs.activities.interfaces;

import com.happybaby.kcs.bd.room.entities.ShoppingCartProduct;
import java.util.List;

public interface ShoppingCartView extends BaseView {

    void loadFinished(List<ShoppingCartProduct> shoppingCartProducts);
    void notifyDataSetChanged();
    void confirmPurchaseEnabled(boolean enabled);
    void setTotalProducts(String sTotalProducts);
    void setTotal(String sTotal);
    void startLoginActivity();
    void showPurchaseMessage();
}
