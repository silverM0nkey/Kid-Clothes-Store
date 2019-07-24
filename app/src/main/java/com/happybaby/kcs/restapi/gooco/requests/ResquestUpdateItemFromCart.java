package com.happybaby.kcs.restapi.gooco.requests;

public class ResquestUpdateItemFromCart {

    String quantity;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public ResquestUpdateItemFromCart(String quantity) {
        this.quantity = quantity;
    }
}
