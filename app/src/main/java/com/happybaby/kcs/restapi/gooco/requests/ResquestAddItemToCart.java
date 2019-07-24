package com.happybaby.kcs.restapi.gooco.requests;

public class ResquestAddItemToCart  extends ResquestUpdateItemFromCart  {

    String variantId;

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public ResquestAddItemToCart(String quantity, String variantId) {
        super(quantity);
        this.variantId = variantId;
    }
}
