package com.happybaby.kcs.presenters;

import com.happybaby.kcs.activities.interfaces.FilterView;
import com.happybaby.kcs.models.FilterListModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class FilterPresenter {

    final static public int DEFAULT_PRICES_VALUE = 0;
    final static public String EMPTY_PRICES_VALUE = "";
    FilterView filterView;
    ArrayList<String> availableColours;
    ArrayList<String> filterColours;
    Integer filterMaxPrice;
    Integer filterMinPrice;
    List<FilterListModel> filterListModelList;

    public FilterPresenter(FilterView filterView, ArrayList<String> availableColours, ArrayList<String> filterColours, Integer filterMaxPrice, Integer filterMinPrice) {
        this.filterView = filterView;
        this.availableColours = availableColours;
        this.filterColours = filterColours;
        this.filterMaxPrice = filterMaxPrice;
        this.filterMinPrice = filterMinPrice;
    }

    public void showPrices() {
        if (filterMaxPrice != DEFAULT_PRICES_VALUE) {
            filterView.setMaxPrice(filterMaxPrice.toString());
        }

        if (filterMinPrice != DEFAULT_PRICES_VALUE) {
            filterView.setMinPrice(filterMinPrice.toString());
        }
    }

    public void loadColorList() {
        filterListModelList = availableColours.
            stream().map(availableColour -> new FilterListModel(availableColour, filterColours != null && filterColours.stream().anyMatch(filterColour -> filterColour.equals(availableColour)))).
            collect(Collectors.toList());
        filterView.setColorList(filterListModelList);
    }

    public void onChangeListColorSelection(int pos) {
        filterListModelList.get(pos).setCheck(!filterListModelList.get(pos).getCheck());
        filterView.notifyDataSetChanged();
    }

    public void onChangeMinPrice(String filterMinPrice){
        if (filterMinPrice.equals(EMPTY_PRICES_VALUE)) {
            this.filterMinPrice = DEFAULT_PRICES_VALUE;
        } else {
            this.filterMinPrice = Integer.valueOf(filterMinPrice);
        }
    }

    public void onChangeMaxPrice(String filterMaxPrice){
        if (filterMaxPrice.equals(EMPTY_PRICES_VALUE)) {
            this.filterMaxPrice = DEFAULT_PRICES_VALUE;
        } else {
            this.filterMaxPrice = Integer.valueOf(filterMaxPrice);
        }
    }

    public void removeFilters() {
        filterView.setMaxPrice(EMPTY_PRICES_VALUE);
        filterView.setMinPrice(EMPTY_PRICES_VALUE);
        filterListModelList.forEach(c -> c.setCheck(false));
        filterView.notifyDataSetChanged();
    }

    public void fillResult() {
        ArrayList<String> selectedColors = filterListModelList.
                stream().filter(c -> c.getCheck()).map(c -> c.getName()).collect(Collectors.toCollection(ArrayList::new));

        filterView.setResults(selectedColors,
                filterMaxPrice != DEFAULT_PRICES_VALUE ? filterMaxPrice : null,
                filterMinPrice != DEFAULT_PRICES_VALUE ? filterMinPrice : null);
    }

    public void unbindView(){
        this.filterView = null;
    }

}
