package com.happybaby.kcs.Helpers;

import android.content.Context;

import com.happybaby.kcs.R;
import com.happybaby.kcs.models.FilterQueryModel;
import com.happybaby.kcs.restapi.gooco.ConnectionsProfile;
import com.happybaby.kcs.restapi.gooco.responses.ResponseProduct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CatalogHelper {

    static public ArrayList<ResponseProduct> filterResults(ArrayList<ResponseProduct> responseResults, FilterQueryModel filterQueryModel) {
        ArrayList<ResponseProduct> finalResponseResults = responseResults.
                stream().filter(p -> (filterQueryModel.getColourFilter().size() == 0 || filterQueryModel.getColourFilter().stream().anyMatch(c -> c.equals(p.getColor()))) &&
                (filterQueryModel.getMinPriceFilter() == null || p.getFinalPrice() >= filterQueryModel.getMinPriceFilter() * 100) &&
                (filterQueryModel.getMaxPriceFilter() == null || p.getFinalPrice() <= filterQueryModel.getMaxPriceFilter() * 100)).collect(Collectors.toCollection(ArrayList::new));

        return finalResponseResults;
    }

    static public List<ResponseProduct> sortResult (List<ResponseProduct> responseResults, int sortBy){
        switch (sortBy) {
            //EXPENSIVE TO CHEAP
            case 0:
                Collections.sort(responseResults, (p1, p2) -> p1.getFinalPrice().compareTo(p2.getFinalPrice()));
                break;
            //CHEAP TO EXPENSIVE
            case 1:
                Collections.sort(responseResults, (p1, p2) -> p2.getFinalPrice().compareTo(p1.getFinalPrice()));
                break;
            //A-Z
            case 2:
                Collections.sort(responseResults, (p1, p2) -> p1.getName().compareTo(p2.getName()));
                break;
            //Z-A
            case 3:
                Collections.sort(responseResults, (p1, p2) -> p2.getName().compareTo(p1.getName()));
                break;
        }
        return  responseResults;
    }

    static public ArrayList<String> getAvailableColors(List<ResponseProduct> responseResults, Context context) {
        List<String> colors;
        if (ConnectionsProfile.FIXED_COLOURS_AND_SIZES){
            String [] arrayColor = context.getResources().getStringArray(R.array.color_array);
            colors = Arrays.asList(arrayColor);
        } else {
            colors = responseResults.stream().map(p -> p.getColor()).collect(Collectors.toList());
            Set<String> set = new HashSet<>(colors);
            colors.clear();
            colors.addAll(set);

        }
        return new ArrayList<>(colors);
    }

}
