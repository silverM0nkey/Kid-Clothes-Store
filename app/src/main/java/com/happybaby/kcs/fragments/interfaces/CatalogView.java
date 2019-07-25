package com.happybaby.kcs.fragments.interfaces;

import android.content.Context;

import com.happybaby.kcs.restapi.gooco.responses.ResponseHome;
import com.happybaby.kcs.restapi.gooco.responses.ResponseProduct;
import java.util.ArrayList;

public interface CatalogView {

    void setFilterVisibility(boolean isVisible);
    void onHomeResponse(ResponseHome responseHome);
    void onProductsResponse(ArrayList<ResponseProduct> products);
    void updateProductsRecyclerListAdapter(ArrayList<ResponseProduct> products);
    void updateSortOptionsListAdapter();
    void setProgressBarGone();
    void setSortByListOptionsGone();
    Context getContext();

}
