package com.happybaby.kcs.models;

import com.happybaby.kcs.restapi.gooco.responses.ResponseSize;

public class SizeModel extends ResponseSize {

    private Boolean check;

    public SizeModel(String variantId, String name, Integer stockQty, boolean check) {
        super(variantId, name, stockQty);
        this.check = check;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public ResponseSize toResponseSize(){
        return new ResponseSize(getVariantId(), getName(), getStockQty());
    }
}
