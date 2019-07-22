package com.happybaby.kcs.restapi.gooco.responses;


import android.os.Parcel;
import android.os.Parcelable;

public class ResponseSize implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ResponseSize createFromParcel(Parcel in) {
            return new ResponseSize(in);
        }

        public ResponseSize[] newArray(int size) {
            return new ResponseSize[size];
        }
    };

    private String variantId;
    private String name;
    private Integer stockQty;

    public ResponseSize(String variantId, String name, Integer stockQty) {
        this.variantId = variantId;
        this.name = name;
        this.stockQty = stockQty;
    }

    public ResponseSize(Parcel in){
        this.variantId = in.readString();
        this.name = in.readString();
        this.stockQty =  in.readInt();
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStockQty() {
        return stockQty;
    }

    public void setStockQty(Integer stockQty) {
        this.stockQty = stockQty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.variantId);
        parcel.writeString(this.name);
        parcel.writeInt(this.stockQty);
    }
}
