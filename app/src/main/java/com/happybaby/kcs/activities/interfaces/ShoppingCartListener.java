package com.happybaby.kcs.activities.interfaces;

public interface ShoppingCartListener {

    void onRemoveProduct(String modelId, String variantId);
    void onChangeQyt(String modelId, String variantId, int qty);
}
