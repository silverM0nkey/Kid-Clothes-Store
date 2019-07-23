package com.happybaby.kcs.models;

import java.util.ArrayList;

public class FilterQueryModel {

    ArrayList<String> colourFilter;
    Integer maxPriceFilter;
    Integer minPriceFilter;

    public FilterQueryModel(ArrayList<String> colourFilter, Integer maxPriceFilter, Integer minPriceFilter) {
        this.colourFilter = colourFilter;
        this.maxPriceFilter = maxPriceFilter;
        this.minPriceFilter = minPriceFilter;
    }

    public FilterQueryModel(){
        this.colourFilter = new ArrayList<>();
        this.maxPriceFilter = null;
        this.minPriceFilter = null;
    }

    public ArrayList<String> getColourFilter() {
        return colourFilter;
    }

    public void setColourFilter(ArrayList<String> colourFilter) {
        this.colourFilter = colourFilter;
    }

    public Integer getMaxPriceFilter() {
        return maxPriceFilter;
    }

    public void setMaxPriceFilter(Integer maxPriceFilter) {
        this.maxPriceFilter = maxPriceFilter;
    }

    public Integer getMinPriceFilter() {
        return minPriceFilter;
    }

    public void setMinPriceFilter(Integer minPriceFilter) {
        this.minPriceFilter = minPriceFilter;
    }

    public boolean anyFilter(){
        return this.colourFilter.size() > 0  || this.maxPriceFilter != null || this.minPriceFilter != null;
    }
}
