package com.happybaby.kcs.presenters;

import android.content.Intent;

import com.happybaby.kcs.helpers.CatalogHelper;
import com.happybaby.kcs.activities.FiltersActivity;
import com.happybaby.kcs.activities.ProductActivity;
import com.happybaby.kcs.fragments.interfaces.CatalogView;
import com.happybaby.kcs.models.FilterQueryModel;
import com.happybaby.kcs.models.fixed.SortOptionItemModel;
import com.happybaby.kcs.presenters.interfaces.IResponseHome;
import com.happybaby.kcs.presenters.interfaces.IResponseProducts;
import com.happybaby.kcs.restapi.gooco.responses.ResponseHome;
import com.happybaby.kcs.restapi.gooco.responses.ResponseProduct;
import com.happybaby.kcs.restapi.gooco.responses.ResponseProductsResults;
import com.happybaby.kcs.restapi.gooco.services.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class CatalogPresenter extends BasePresenter implements IResponseHome, IResponseProducts {

    private FilterQueryModel filterQueryModel;
    private ArrayList<ResponseProduct> responseResults;
    private ArrayList<ResponseProduct> products;
    private CatalogView catalogView;
    private ArrayList<String> availableColors;
    private List<SortOptionItemModel> sortOptions;

    private String storeId;
    private String categoryName;
    private Services services;
    private String [] arrayColor;

    public CatalogPresenter(CatalogView catalogView, String storeId, String [] arrayColor) {
        super();
        this.filterQueryModel = new FilterQueryModel();
        this.services = new Services();
        this.catalogView =  catalogView;
        this.storeId = storeId;
        this.arrayColor = arrayColor;
    }

    public void getHomeCategories() {
        this.services.getHomeCategories(this.storeId, this);
    }

    public void onResponseHome(ResponseHome responseHome){
        if (responseHome != null) {
            catalogView.onHomeResponse(responseHome);
        } else {
            catalogView.showErrorServerMessage();
        }
    }

    public void onResponseHomeFail(){
        catalogView.showConnectionErrorActivity();
    }

    public void getProducts(String categoryId, String categoryName) {
        this.categoryName = categoryName;
        this.services.getProducts(this.storeId, categoryId, this);
    }

    public void onResponseProductsResults(ResponseProductsResults responseProducts) {
        if (responseProducts != null) {
            responseResults = new ArrayList<>(responseProducts.getResults());
            products = (ArrayList<ResponseProduct>) responseResults.clone();
            catalogView.onProductsResponse(products);
            availableColors = CatalogHelper.getAvailableColors(products, this.arrayColor);
        } else {
            catalogView.showErrorServerMessage();
            catalogView.setProgressBarGone();
        }
    }

    public void onResponseProductsFail() {
        catalogView.setProgressBarGone();
        catalogView.showConnectionErrorActivity();
    }

    public void filterResult(ArrayList <String> colourFilter, int maxPriceFilter, int  minPriceFilter) {
        filterQueryModel =  new FilterQueryModel(colourFilter, maxPriceFilter == 0 ? null: maxPriceFilter, minPriceFilter == 0 ? null : minPriceFilter);
        products = CatalogHelper.filterResults(responseResults, filterQueryModel);
        catalogView.updateProductsRecyclerListAdapter(products);
        catalogView.setFilterVisibility(filterQueryModel.anyFilter());
    }

    public void sortResult(int sortBy) {
        for (SortOptionItemModel item: sortOptions){
            item.setCheck(false);
        }
        sortOptions.get(sortBy).setCheck(true);
        catalogView.updateSortOptionsListAdapter();
        CatalogHelper.sortResult(products, sortBy);
        catalogView.setSortByListOptionsGone();
        catalogView.updateProductsRecyclerListAdapter(products);
    }

    public List<SortOptionItemModel> getSortOptions(String[] options){
        this.sortOptions =  Arrays.stream(options).map(SortOptionItemModel::new).collect(Collectors.toList());
        return sortOptions;
    }

    public Intent addProductParams(Intent intent, String modelId) {
        ResponseProduct responseProduct = this.products.stream().filter(p -> p.getModelId().equals(modelId)).findAny().orElse(null);
        intent.putExtra(ProductActivity.PARAM_STORE_ID, storeId);
        intent.putExtra(ProductActivity.PARAM_PRODUCT_NAME, responseProduct.getName());
        intent.putExtra(ProductActivity.PARAM_MODEL_ID, responseProduct.getModelId());
        intent.putExtra(ProductActivity.PARAM_FINAL_PRICE, responseProduct.getFinalPrice());
        intent.putExtra(ProductActivity.PARAM_ORIGINAL_PRICE, responseProduct.getOriginalPrice());
        intent.putExtra(ProductActivity.PARAM_FINAL_PRICE_TYPE, responseProduct.getFinalPriceType());
        intent.putExtra(ProductActivity.PARAM_CURRENCY, responseProduct.getCurrency());
        intent.putExtra(ProductActivity.PARAM_SKU, responseProduct.getSku());
        intent.putExtra(ProductActivity.PARAM_COMPOSITION, responseProduct.getComposition());
        intent.putExtra(ProductActivity.PARAM_CARE, responseProduct.getCare());
        intent.putExtra(ProductActivity.PARAM_URL, responseProduct.getUrl());
        intent.putExtra(ProductActivity.PARAM_COLOR, responseProduct.getColor());

        ArrayList<String> listOfStrings = new ArrayList<>(responseProduct.getImages().size());
        listOfStrings.addAll(responseProduct.getImages());

        intent.putExtra(ProductActivity.PARAM_IMAGES, listOfStrings);
        intent.putParcelableArrayListExtra(ProductActivity.PARAM_SIZES, new ArrayList<>(responseProduct.getSizes()));
        return intent;
    }

    public Intent addFilterParams(Intent intent) {
        intent.putExtra(FiltersActivity.PARAM_MAX_PRICE_FILTER, filterQueryModel.getMaxPriceFilter());
        intent.putExtra(FiltersActivity.PARAM_MIN_PRICE_FILTER, filterQueryModel.getMinPriceFilter());
        intent.putExtra(FiltersActivity.PARAM_COLOUR_FILTER, filterQueryModel.getColourFilter());
        intent.putExtra(FiltersActivity.PARAM_AVAILABLE_COLOURS, this.availableColors);
        return intent;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void unbindView(){
        this.catalogView = null;
    }
}