package com.happybaby.kcs.restapi.gooco.responses;

import java.util.List;

public class ResponseStore {

    private String name;
    private String countryCode;
    private String storeCode;
    private String websiteCode;

    List<ResponseStoreView> storeViews;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getWebsiteCode() {
        return websiteCode;
    }

    public void setWebsiteCode(String websiteCode) {
        this.websiteCode = websiteCode;
    }

    public List<ResponseStoreView> getStoreViews() {
        return storeViews;
    }

    public void setStoreViews(List<ResponseStoreView> storeViews) {
        this.storeViews = storeViews;
    }
}
