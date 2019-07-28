package com.happybaby.kcs.activities.interfaces;

import android.content.Context;

import com.happybaby.kcs.models.FilterListModel;

import java.util.ArrayList;
import java.util.List;

public interface FilterView {

    void setMinPrice(String minPrice);
    void setMaxPrice(String maxPrice);
    void setColorList(List<FilterListModel> filterListModelList);
    void notifyDataSetChanged();
    void setResults(ArrayList<String> selectedColors, Integer maxPrice, Integer minPrice);
    Context getContext();
}
