package com.happybaby.kcs.activities.interfaces;

import android.content.Context;
import com.happybaby.kcs.bd.room.entities.ShoppingCartProduct;
import java.util.List;

public interface ShoppingCartView {

    void loadFinished(List<ShoppingCartProduct> shoppingCartProducts);
    void notifyDataSetChanged();
    void confirmPurchaseEnabled(boolean enabled);
    void setTotalProducts(String sTotalProducts);
    void setTotal(String sTotal);
    void startLoginActivity();
    void showPurchaseMessage();
    Context getContext();
}
